package com.example.drache_o_metre.data.interact.responses;

import com.google.gson.annotations.SerializedName;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyWeatherResponse {
    private List<DailyForecastItem> list;

    public List<DailyForecastItem> getForecastList() {
        return list;
    }

    public void setForecastList(List<DailyForecastItem> list) {
        this.list = list;
    }

    public static class DailyForecastItem {
        private long dt;
        private Temp temp;
        private List<Weather> weather;

        public long getDt() {
            return dt;
        }

        public void setDt(long dt) {
            this.dt = dt;
        }

        public Temp getTemperature() {
            return temp;
        }

        public void setTemperature(Temp temp) {
            this.temp = temp;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public void setWeather(List<Weather> weather) {
            this.weather = weather;
        }

        public String getIconCode() {
            // Récupère l'icône météo à partir de la première entrée de la liste weather
            return weather != null && !weather.isEmpty() ? weather.get(0).getIcon() : null;
        }

        public String getDayName() {
            Date date = new Date(dt * 1000L); // Convertir le timestamp en millisecondes
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE", Locale.getDefault()); // Nom du jour en format complet
            return sdf.format(date);
        }

        public double getPrecipitationProbability() {
            // Retourne la probabilité de précipitation (pop)
            return temp != null ? temp.getPop() : 0.0;
        }
    }

    public static class Temp {
        private float day;
        private float min;
        private float max;
        private float night;
        private float eve;
        private float morn; // Température du matin
        private double pop; // Probabilité de précipitation

        public float getDay() {
            return day;
        }

        public void setDay(float day) {
            this.day = day;
        }

        public float getMin() {
            return min;
        }

        public void setMin(float min) {
            this.min = min;
        }

        public float getMax() {
            return max;
        }

        public void setMax(float max) {
            this.max = max;
        }

        public float getNight() {
            return night;
        }

        public void setNight(float night) {
            this.night = night;
        }

        public float getEve() {
            return eve;
        }

        public void setEve(float eve) {
            this.eve = eve;
        }

        public float getMorn() {
            return morn;
        }

        public void setMorn(float morn) {
            this.morn = morn;
        }

        public double getPop() {
            return pop;
        }

        public void setPop(double pop) {
            this.pop = pop;
        }
    }

    public static class Weather {
        private String main;
        private String description;
        private String icon;

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}


