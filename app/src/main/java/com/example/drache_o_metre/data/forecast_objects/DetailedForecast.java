package com.example.drache_o_metre.data.forecast_objects;

public class DetailedForecast {
    private String dayName;
    private int morningIconResId;
    private int afternoonIconResId;
    private String morningTemp;
    private String afternoonTemp;
    private String pop;

    public DetailedForecast(String dayName, int morningIconResId, int afternoonIconResId, String morningTemp, String afternoonTemp, String pop) {
        this.dayName = dayName;
        this.morningIconResId = morningIconResId;
        this.afternoonIconResId = afternoonIconResId;
        this.morningTemp = morningTemp;
        this.afternoonTemp = afternoonTemp;
        this.pop = pop;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public int getMorningIconResId() {
        return morningIconResId;
    }

    public void setMorningIconResId(int morningIconResId) {
        this.morningIconResId = morningIconResId;
    }

    public int getAfternoonIconResId() {
        return afternoonIconResId;
    }

    public void setAfternoonIconResId(int afternoonIconResId) {
        this.afternoonIconResId = afternoonIconResId;
    }

    public String getMorningTemp() {
        return morningTemp;
    }

    public void setMorningTemp(String morningTemp) {
        this.morningTemp = morningTemp;
    }

    public String getAfternoonTemp() {
        return afternoonTemp;
    }

    public void setAfternoonTemp(String afternoonTemp) {
        this.afternoonTemp = afternoonTemp;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }
}

