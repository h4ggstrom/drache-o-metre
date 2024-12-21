package com.example.drache_o_metre.data.forecast_objects;

public class DailyForecast {
    private final String dayName;
    private final int weatherIcon;
    private final double precipitationProbability;

    public DailyForecast(String dayName, int weatherIcon, double precipitationProbability) {
        this.dayName = dayName;
        this.weatherIcon = weatherIcon;
        this.precipitationProbability = precipitationProbability;
    }

    public String getDayName() {
        return dayName;
    }

    public int getWeatherIcon() {
        return weatherIcon;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }
}


