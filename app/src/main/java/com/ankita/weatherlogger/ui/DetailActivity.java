package com.ankita.weatherlogger.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ankita.weatherlogger.R;
import com.ankita.weatherlogger.models.WeatherResponses;
import com.ankita.weatherlogger.utils.GifImageView;
import com.ankita.weatherlogger.viewmodels.WeatherListViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    TextView tvTemp,tvRain,tvSky,tvHumidity,tv_temperature,tvDate,tvVisibility,tvPressure;
    GifImageView imgTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTemp = findViewById(R.id.tv_city);
        tvRain = findViewById(R.id.tv_rain);
        tvSky = findViewById(R.id.tv_sky);
        tvHumidity = findViewById(R.id.tv_humidity);
        tv_temperature = findViewById(R.id.tv_temperature);
        tvDate = findViewById(R.id.tv_date);
        tvVisibility = findViewById(R.id.tv_visibility);
        tvPressure = findViewById(R.id.tv_pressure);
        imgTemp = findViewById(R.id.img_temp);

        setTitle("Detail Report");
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        WeatherListViewModel weatherListViewModel = ViewModelProviders.of(this).get(WeatherListViewModel.class);

      final int position =  getIntent().getExtras().getInt("position");
        weatherListViewModel.getAllResponses().observe(this, new Observer<List<WeatherResponses>>() {
            @Override
            public void onChanged(@Nullable final List<WeatherResponses> responses) {
                // Update the cached copy of the words in the adapter.
                if(responses.size()>0){
                    WeatherResponses weatherResponses  = responses.get(position);
                    double tempDouble = weatherResponses.getMain().getTemp();
                    int tempInt = (int) Math.round(tempDouble-273.15);
                    String tempString = String.valueOf(tempInt);
                    tvTemp.setText(tempString+"°C");
                    tv_temperature.setText("Place : " + weatherResponses.getName());
                    tvSky.setText("Seems to be a " + weatherResponses.getResults().get(0).getDescription());
                    tvRain.setText("Wind Speed : " + weatherResponses.getWind().getSpeed());
                    tvDate.setText("Date and Time : " + weatherResponses.getDate());
                    tvHumidity.setText("Humidity : "+weatherResponses.getMain().getHumidity() + "%");
                    tvPressure.setText("Pressure : "+weatherResponses.getMain().getPressure());
                    tvVisibility.setText("Visibility : "+weatherResponses.getVisibility());
                    if(weatherResponses.getResults().get(0).getDescription().contains("clear")){
                        imgTemp.setGifImageResource(R.drawable.clear_sky);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("few clouds")){
                        imgTemp.setGifImageResource(R.drawable.fewclouds);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("scattered")){
                        imgTemp.setGifImageResource(R.drawable.scattered);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("overcast")){
                        imgTemp.setGifImageResource(R.drawable.scattered);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("rain")){
                        imgTemp.setGifImageResource(R.drawable.shower_rain);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("thunderstorm")){
                        imgTemp.setGifImageResource(R.drawable.thunder);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("snow")){
                        imgTemp.setGifImageResource(R.drawable.snow);
                    }
                    if(weatherResponses.getResults().get(0).getDescription().contains("mist") || weatherResponses.getResults().get(0).getDescription().contains("smoke")){
                        imgTemp.setGifImageResource(R.drawable.mist);
                    }
                  /*  Glide.with(DetailActivity.this)
                            .load("http://openweathermap.org/img/wn/"+weatherResponses.getResults().get(0).getIcon() + "@2x.png")
                            .into(imgTemp);*/
                }
            }
        });


    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}