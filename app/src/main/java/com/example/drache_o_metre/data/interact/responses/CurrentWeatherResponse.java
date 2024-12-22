package com.example.drache_o_metre.data.interact.responses;

import com.google.gson.annotations.SerializedName;

public class CurrentWeatherResponse {

    @SerializedName("main")
    private Main main;

    @SerializedName("weather")
    private Weather[] weather;

    @SerializedName("name")
    private String cityName;

    // Température en Kelvin convertie en Celsius
    public double getTemperature() {
        return Math.round(main.temp - 273.15); // Convertir de Kelvin à Celsius
    }

    // Code de l'icône de la météo
    public String getIconCode() {
        return weather[0].icon;
    }

    // Nom de la ville
    public String getCityName() {
        return cityName;
    }

    // Classe interne pour les informations principales (température)
    public static class Main {
        @SerializedName("temp")
        private double temp;
    }

    // Classe interne pour les informations sur la météo (icône)
    public static class Weather {
        @SerializedName("icon")
        private String icon;
    }
}

