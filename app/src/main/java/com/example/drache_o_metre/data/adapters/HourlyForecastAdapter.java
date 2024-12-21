package com.example.drache_o_metre.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.R;
import com.example.drache_o_metre.data.forecast_objects.HourlyForecast;

import java.util.List;

public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourlyViewHolder> {
    private List<HourlyForecast> hourlyForecastList;

    public HourlyForecastAdapter(List<HourlyForecast> hourlyForecastList) {
        this.hourlyForecastList = hourlyForecastList;
    }

    @NonNull
    @Override
    public HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_forecast, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyViewHolder holder, int position) {
        HourlyForecast forecast = hourlyForecastList.get(position);
        holder.time.setText(forecast.getTime());
        holder.temperature.setText(forecast.getTemperature());

        // load icon from resources
        String iconName = forecast.getIcon();
        int iconResId = holder.itemView.getContext().getResources().getIdentifier(iconName, "drawable", holder.itemView.getContext().getPackageName());
        holder.weatherIcon.setImageResource(iconResId);
    }

    @Override
    public int getItemCount() {
        return hourlyForecastList.size();
    }

    public static class HourlyViewHolder extends RecyclerView.ViewHolder {
        TextView time, temperature;
        ImageView weatherIcon;

        public HourlyViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            temperature = itemView.findViewById(R.id.temperature);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
        }
    }
}

