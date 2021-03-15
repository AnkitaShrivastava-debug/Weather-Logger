package com.ankita.weather.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.ankita.weather.models.WeatherList;
import com.ankita.weather.models.WeatherResponses;
import com.ankita.weather.repositories.WeatherListRepository;
import com.ankita.weather.utils.Resource;

import java.util.List;

public class WeatherListViewModel extends AndroidViewModel {

    private static final String TAG = "WeatherListViewModel";

    private WeatherListRepository mWeatherListRepository;
    private boolean isPerformingQuery;
    public static final String QUERY_EXHAUSTED = "Query is exhausted.";
    private LiveData<List<WeatherResponses>> allResponses;

    private final MediatorLiveData<Resource<List<WeatherResponses>>> _data = new MediatorLiveData<>();

    public LiveData<Resource<List<WeatherResponses>>> data() {
        return _data;
    }

    public WeatherListViewModel(@NonNull Application application) {
        super(application);
        mWeatherListRepository = WeatherListRepository.getInstance(application);
        allResponses = mWeatherListRepository.getAllResponses();
    }

    public LiveData<List<WeatherResponses>> getAllResponses() {
        return allResponses;
    }

    public void getWeatherDetails(String lat, String lon, String apiKey ) {
        isPerformingQuery = true;
        final LiveData<Resource<List<WeatherResponses>>> source = mWeatherListRepository.getWeatherDetails(lat, lon, apiKey);
        Log.d(TAG, "getWeather: called.");
        _data.addSource(source, new Observer<Object>() {

            @Override
            public void onChanged(Object resource) {
                Log.d(TAG, "onChanged: called.");

                if(resource!= null){
                    _data.setValue((Resource<List<WeatherResponses>>) resource);
                    if(((Resource<List<WeatherResponses>>) resource).status == Resource.Status.SUCCESS ){
                        isPerformingQuery = false;
                        if(((Resource<List<WeatherResponses>>) resource).data != null) {

                            Log.d(TAG, "onChanged: query is EXHAUSTED...");
                            _data.setValue(new Resource<List<WeatherResponses>>(
                                    Resource.Status.ERROR,
                                    ((Resource<List<WeatherResponses>>) resource).data,
                                    QUERY_EXHAUSTED
                            ));
                            isPerformingQuery = true;

                        }
                        // must remove or it will keep listening to repository
                        _data.removeSource(source);
                    }
                    else if(((Resource<List<WeatherResponses>>) resource).status == Resource.Status.ERROR ){
                        isPerformingQuery = false;
                        _data.removeSource(source);
                    }
                }else{
                    _data.removeSource(source);
                }

               /* _data.removeSource(source);
                if(resource.status == Resource.Status.SUCCESS) {
                    Log.d(TAG, "onChanged: ApiSuccessResponse.");
                    //WeatherResponses wr = (WeatherResponses) ((ApiResponse.ApiSuccessResponse) response).getBody() ;
                    _data.setValue(resource);
                }
                else if(resource.status == Resource.Status.ERROR){
                    Log.d(TAG, "onChanged: ApiErrorResponse");
                    _data.removeSource(source);
                }*/
            }
        });
    }

    public void printWeather(List<WeatherList> list){
        for (WeatherList weather : list) {
            Log.d(TAG, "onChanged Ankita: " + weather.toString());
        }
    }
}
