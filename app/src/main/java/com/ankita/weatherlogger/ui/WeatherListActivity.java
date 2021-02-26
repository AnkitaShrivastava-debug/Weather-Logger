package com.ankita.weatherlogger.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ankita.weatherlogger.R;
import com.ankita.weatherlogger.adapters.RecyclerWeatherAdapter;
import com.ankita.weatherlogger.models.WeatherResponses;
import com.ankita.weatherlogger.utils.Constants;
import com.ankita.weatherlogger.utils.Resource;
import com.ankita.weatherlogger.utils.Utility;
import com.ankita.weatherlogger.utils.VerticalSpacingItemDecorator;
import com.ankita.weatherlogger.viewmodels.WeatherListViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.util.List;

public class WeatherListActivity extends BaseActivity {

    private static final String TAG = "WeatherListActivity";
    private RecyclerView mRecyclerView;
    TextView tv_save;
    LinearLayout llDEsc,llMain;
    private RecyclerWeatherAdapter mRecyclerWeatherAdapter;
    private WeatherListViewModel mWeatherListViewModel;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    Location location;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setContentView(R.layout.activity_weather_list);
        Explode enterTrnasition = new Explode();
        Explode exitTransition = new Explode();
        setTitle("Add Weather");
        // shows the transition for 2 seconds
        enterTrnasition.setDuration(2000);
        exitTransition.setDuration(2000);

        initComponents();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        subscribeObservers();
        mWeatherListViewModel.getAllResponses().observe(this, new Observer<List<WeatherResponses>>() {
            @Override
            public void onChanged(@Nullable final List<WeatherResponses> responses) {
                // Update the cached copy of the words in the adapter.
                if(responses.size()>0){
                    initRecyclerView(responses);
                }
            }
        });        // method to get the location
        getLastLocation();
    }

    private void initRecyclerView(List<WeatherResponses> responses) {
        llMain.setVisibility(View.VISIBLE);
        llDEsc.setVisibility(View.GONE);
        mRecyclerWeatherAdapter.setData(responses);
    }

    private void initComponents() {
        mWeatherListViewModel = ViewModelProviders.of(this).get(WeatherListViewModel.class);
        mRecyclerView = findViewById(R.id.recyclerView_weather);
        tv_save = findViewById(R.id.tv_save);
        llDEsc = findViewById(R.id.ll_desc);
        llMain = findViewById(R.id.ll_main);
        mRecyclerWeatherAdapter = new RecyclerWeatherAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        mRecyclerView.addItemDecoration(itemDecorator);
        mRecyclerView.setAdapter(mRecyclerWeatherAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != location) {
                    getData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }else
                    Toast.makeText(WeatherListActivity.this, "Sorry!! Couldn't find your location", Toast.LENGTH_SHORT).show();

            }
        });
        // mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData(String lat,String lon){
        mWeatherListViewModel.getWeatherDetails(
                lat,
                lon,
                Constants.API_KEY
        );
    }

    private void subscribeObservers(){
        Log.d(TAG, "subscribeObservers: " + mWeatherListViewModel);

        mWeatherListViewModel.data().observe(this, new Observer<Resource<List<WeatherResponses>>>() {

            @Override
            public void onChanged(Resource<List<WeatherResponses>> weatherResponsesResource) {
                if(weatherResponsesResource != null){
                    if(weatherResponsesResource.status != null){
                        switch (weatherResponsesResource.status){

                            case LOADING:{
                                showProgressBar(true);
                                break;
                            }

                            case ERROR:{
                                //    Log.e(TAG, "onChanged: status: ERROR, Weather: " + weatherResponsesResource.data.getResults() );
                                Log.e(TAG, "onChanged: ERROR message: " + weatherResponsesResource.message );

                                break;
                            }

                            case SUCCESS:{
                                Log.d(TAG, "onChanged: cache has been refreshed.");
                                Log.d(TAG, "onChanged: status: SUCCESS, Weather: " + weatherResponsesResource.data.get(0).getResults());
                                showProgressBar(false);
                                // do something with the data
                                initRecyclerView(weatherResponsesResource.data);

                                //test
                               /* List<WeatherList> data = weatherResponsesResource.data.getResults();
                                mWeatherListViewModel.printWeather(data);*/
                                break;
                            }
                        }
                    }
                }
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {
                           /* String city = null;
                            Geocoder geocoder = new Geocoder(
                                    WeatherListActivity.this, Locale
                                    .getDefault());
                            List<Address> addresses;
                            try {
                                addresses = geocoder.getFromLocation(location.getLatitude(),
                                        location.getLongitude(), 1);
                                city = addresses.get(0).getAddressLine(0);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }*/


                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);

            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            switch (requestCode) {
                case 1:
                    getLastLocation();
                    break;
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_save:
                showLocationDialog();
                break;
        }
        return true;
    }

    public void showLocationDialog(){
        TextView tvManuallyAuto, tvAllowAccess,tvSave;
        final LinearLayout llMain,llManual;
        final EditText edtCity;

        final BottomSheetDialog dialog = new BottomSheetDialog(WeatherListActivity.this, R.style.BottomSheetDialog);
        dialog.setContentView(R.layout.dialog_auto_manual);
        tvManuallyAuto = dialog.findViewById(R.id.tv_enter_manually);
        tvAllowAccess = dialog.findViewById(R.id.tv_allow_access);
        llMain = dialog.findViewById(R.id.ll_main);
        llManual = dialog.findViewById(R.id.ll_manual);
        tvSave = dialog.findViewById(R.id.tv_save);
        edtCity = dialog.findViewById(R.id.edt_city);

        tvManuallyAuto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMain.setVisibility(View.GONE);
                llManual.setVisibility(View.VISIBLE);

            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Utility.hideKeyboard(WeatherListActivity.this);
                if(edtCity.getText().toString().length()>0){
                    LatLng latLng = checkGeocoder(edtCity.getText().toString());
                    if(null != latLng) {
                        getData(String.valueOf(latLng.latitude), String.valueOf(latLng.longitude));
                    }else
                        Toast.makeText(WeatherListActivity.this, "Sorry!! Couldn't find your location", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(WeatherListActivity.this, "Please enter the name of the city", Toast.LENGTH_SHORT).show();
            }
        });

        tvAllowAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(null != location) {
                    getData(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()));
                }else
                    Toast.makeText(WeatherListActivity.this, "Sorry!! Couldn't find your location", Toast.LENGTH_SHORT).show();

            }
        });
        dialog.show();
    }

    public LatLng checkGeocoder(String cityName) {
        if (Geocoder.isPresent()) {
            LatLng ll = new LatLng(0, 0);
            try {
                String location = cityName;
                Geocoder gc = new Geocoder(this);
                List<Address> addresses = gc.getFromLocationName(location, 1); // get the found Address Objects
                for (Address a : addresses) {
                    if (a.hasLatitude() && a.hasLongitude()) {
                        ll = new LatLng(a.getLatitude(), a.getLongitude());
                    }
                }
                return ll;
            } catch (IOException e) {
                // handle the exception
            }
            return ll;
        }
        return null;

    }
}
