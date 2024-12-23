package com.example.drache_o_metre.data.forecast_objects;

import static com.example.drache_o_metre.data.interact.WeatherIconUtils.getWeatherIconResource;

public class DailyForecast {
    private final String dayName;
    private final String weatherIcon;
    private final double precipitationProbability;
    private final float mornTemp; // Température du matin
    private final float maxTemp;  // Température maximale de la journée

    public DailyForecast(String dayName, String weatherIcon, double precipitationProbability, float mornTemp, float maxTemp) {
        this.dayName = dayName;
        this.weatherIcon = getWeatherIconResource(weatherIcon);
        this.precipitationProbability = precipitationProbability;
        this.mornTemp = mornTemp;
        this.maxTemp = maxTemp;
    }

    public String getDayName() {
        return dayName;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }

    public float getMornTemp() {
        return mornTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }
}




