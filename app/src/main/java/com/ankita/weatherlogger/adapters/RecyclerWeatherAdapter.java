package com.ankita.weatherlogger.adapters;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ankita.weatherlogger.R;
import com.ankita.weatherlogger.models.WeatherResponses;
import com.ankita.weatherlogger.ui.DetailActivity;
import com.ankita.weatherlogger.ui.WeatherListActivity;
import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class RecyclerWeatherAdapter extends RecyclerView.Adapter<RecyclerWeatherAdapter.WeatherViewHolder> {

    private Context mContext;
    WeatherListActivity activity;
    private List<WeatherResponses> mWeatherResponses= new ArrayList<>();

    public RecyclerWeatherAdapter(Context context) {
        this.mContext = context;
        this.activity = (WeatherListActivity) context;
    }

    public void setData(List<WeatherResponses> weatherResponses){
        this.mWeatherResponses.clear();
        this.mWeatherResponses.addAll(weatherResponses);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerWeatherAdapter.WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_recylcer_list_item, parent, false);
        return new WeatherViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerWeatherAdapter.WeatherViewHolder holder, final int position) {

        double tempDouble = mWeatherResponses.get(position).getMain().getTemp();
        int tempInt = (int) Math.round(tempDouble-273.15);
        String tempString = String.valueOf(tempInt);
        holder.mTemperature.setText(tempString+"°C");

        Glide.with(mContext)
                .load("http://openweathermap.org/img/wn/"+mWeatherResponses.get(position).getResults().get(0).getIcon() + "@2x.png")
                .into(holder.weather);

        holder.mDescription.setText("Seems to be "+mWeatherResponses.get(position).getResults().get(0).getDescription());

        // Set the name
        holder.mName.setText( mWeatherResponses.get(position).getName());

        // Set the country
        holder.mCountry.setText( mWeatherResponses.get(position).getDate());

        holder.mViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("position",  position);
                mContext.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWeatherResponses.size();
    }

    public class WeatherViewHolder extends RecyclerView.ViewHolder {

        private TextView mTemperature;
        private TextView mDescription;
        private TextView mName;
        private TextView mCountry;
        private TextView mViewDetails;
        ImageView weather;

        public WeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            mTemperature = itemView.findViewById(R.id.tv_temperature);
            mDescription = itemView.findViewById(R.id.tv_description);
            mName = itemView.findViewById(R.id.tv_name);
            mCountry = itemView.findViewById(R.id.tv_date);
            weather = itemView.findViewById(R.id.img_weather);
            mViewDetails = itemView.findViewById(R.id.tv_details);
        }
    }
}
