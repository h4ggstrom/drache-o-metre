package com.example.drache_o_metre.data.interact.responses;

import java.util.List;

public class HourlyWeatherResponse {
    public List<Hourly> list;

    public static class Hourly {
        public long dt;
        public Main main;
        public List<Weather> weather;
    }

    public static class Main {
        public float temp;
    }

    public static class Weather {
        public String icon;
    }
}


