package com.example.drache_o_metre.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.R;
import com.example.drache_o_metre.data.DailyForecast;

import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyViewHolder> {

    private final List<DailyForecast> dailyForecastList;

    public DailyForecastAdapter(List<DailyForecast> dailyForecastList) {
        this.dailyForecastList = dailyForecastList;
    }

    @NonNull
    @Override
    public DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_forecast, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyViewHolder holder, int position) {
        DailyForecast forecast = dailyForecastList.get(position);
        holder.dayName.setText(forecast.getDayName());
        holder.precipitationProbability.setText(forecast.getPrecipitationProbability() + "%");
        holder.weatherIcon.setImageResource(forecast.getWeatherIcon());
    }

    @Override
    public int getItemCount() {
        return dailyForecastList.size();
    }

    public static class DailyViewHolder extends RecyclerView.ViewHolder {
        TextView dayName;
        ImageView weatherIcon;
        TextView precipitationProbability;

        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            dayName = itemView.findViewById(R.id.dayName);
            weatherIcon = itemView.findViewById(R.id.weatherIcon);
            precipitationProbability = itemView.findViewById(R.id.precipitationProbability);
        }
    }
}
