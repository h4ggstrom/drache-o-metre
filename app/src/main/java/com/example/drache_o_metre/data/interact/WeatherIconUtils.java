package com.example.drache_o_metre.data.interact;

import com.example.drache_o_metre.R;

public class WeatherIconUtils {

    /**
     * Get the corresponding weather icon resource based on the weather code.
     * @param weatherCode the weather code returned by the API
     * @return the corresponding weather icon resource
     */
    public static String getWeatherIconResource(String weatherCode) {
        switch (weatherCode) {
            case "01d":
            case "01n":
                return "sunny";
            case "02d":
            case "02n":
                return "few_clouds";
            case "03d":
            case "03n":
                return "scattered_clouds";
            case "04d":
            case "04n":
                return "broken_clouds";
            case "09d":
            case "09n":
                return "shower_rain";
            case "10d":
            case "10n":
                return "rain";
            case "11d":
            case "11n":
                return "thunderstorm";
            case "13d":
            case "13n":
                return "snow";
            case "50d":
            case "50n":
                return "mist";
            default:
                return "";
        }
    }
}

