package com.example.drache_o_metre.data;

/**
 * HourlyForecast is a class that represents a forecast for a specific time.
 *
 * @author Robin de Angelis
 * @version 1.0.0
 */
public class HourlyForecast {
    private String time;
    private String temperature;
    private String icon;

    /**
     * HourlyForecast constructor..
     *
     * @param time Time of the forecast
     * @param temperature Temperature of the forecast
     * @param icon Icon of the forecast
     */
    public HourlyForecast(String time, String temperature, String icon) {
        this.time = time;
        this.temperature = temperature;
        this.icon = icon;
    }

    // Getters
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    // setters
    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

