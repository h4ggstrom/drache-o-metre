package com.example.drache_o_metre.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drache_o_metre.R;
import com.example.drache_o_metre.data.forecast_objects.DetailedForecast;

import java.util.List;

public class DetailedForecastAdapter extends RecyclerView.Adapter<DetailedForecastAdapter.DetailedForecastViewHolder> {

    private List<DetailedForecast> forecastList;

    public DetailedForecastAdapter(List<DetailedForecast> forecastList) {
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public DetailedForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detailed_forecast, parent, false);
        return new DetailedForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailedForecastViewHolder holder, int position) {
        DetailedForecast forecast = forecastList.get(position);

        holder.dayNameTextView.setText(forecast.getDayName());
        String mornIcon = forecast.getMorningIconResId();
        int mornIconId = holder.itemView.getContext().getResources().getIdentifier(mornIcon, "drawable", holder.itemView.getContext().getPackageName());
        holder.morningIconImageView.setImageResource(mornIconId);

        String afternoonIcon = forecast.getAfternoonIconResId();
        int afternoonIconId = holder.itemView.getContext().getResources().getIdentifier(afternoonIcon, "drawable", holder.itemView.getContext().getPackageName());
        holder.afternoonIconImageView.setImageResource(afternoonIconId);
        holder.morningTempTextView.setText(forecast.getMorningTemp());
        holder.afternoonTempTextView.setText(forecast.getAfternoonTemp());
        holder.popTextView.setText(forecast.getPop());
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public static class DetailedForecastViewHolder extends RecyclerView.ViewHolder {

        TextView dayNameTextView;
        ImageView morningIconImageView;
        ImageView afternoonIconImageView;
        TextView morningTempTextView;
        TextView afternoonTempTextView;
        TextView popTextView;

        public DetailedForecastViewHolder(@NonNull View itemView) {
            super(itemView);
            dayNameTextView = itemView.findViewById(R.id.dayNameTextView);
            morningIconImageView = itemView.findViewById(R.id.morningWeatherIcon);
            afternoonIconImageView = itemView.findViewById(R.id.afternoonWeatherIcon);
            morningTempTextView = itemView.findViewById(R.id.morningTemperatureTextView);
            afternoonTempTextView = itemView.findViewById(R.id.afternoonTemperatureTextView);
            popTextView = itemView.findViewById(R.id.popTextView);
        }
    }
}

