package com.example.drache_o_metre.data;

public class DailyForecast {
    private final String dayName;
    private final int weatherIcon;
    private final int precipitationProbability;

    public DailyForecast(String dayName, int weatherIcon, int precipitationProbability) {
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

    public int getPrecipitationProbability() {
        return precipitationProbability;
    }
}


