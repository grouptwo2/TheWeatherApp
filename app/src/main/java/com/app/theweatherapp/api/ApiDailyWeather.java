package com.app.theweatherapp.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Beyeta Sanu
 * <p>
 * on 11/19/16.
 */

public class ApiDailyWeather {
    @SerializedName("fcst_valid")
    private long time;
    @SerializedName("fcst_valid_local")
    private String timeString;
    @SerializedName("min_temp")
    private int minimumTemperature;
    @SerializedName("max_temp")
    private int maximumTemperature;
    private ApiResourceWeatherCondition day;
    private ApiResourceWeatherCondition night;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(Integer minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public Integer getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(Integer maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public ApiResourceWeatherCondition getDay() {
        return day;
    }

    public void setDay(ApiResourceWeatherCondition day) {
        this.day = day;
    }

    public ApiResourceWeatherCondition getNight() {
        return night;
    }

    public void setNight(ApiResourceWeatherCondition night) {
        this.night = night;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
