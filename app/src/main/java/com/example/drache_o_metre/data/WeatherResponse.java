package com.example.drache_o_metre.data;

/**
 * WeatherResponse is a class that represents the response from the OpenWeatherMap API.
 *
 * @author Robin de Angelis
 * @version 1.0.0
 */
public class WeatherResponse {

    private Main main;
    private String name;

    public Main getMain() {
        return main;
    }

    public String getName() {
        return name;
    }

    public static class Main {
        private double temp;
        private double humidity;

        public double getTemp() {
            return temp;
        }

        public double getHumidity() {
            return humidity;
        }
    }
}
