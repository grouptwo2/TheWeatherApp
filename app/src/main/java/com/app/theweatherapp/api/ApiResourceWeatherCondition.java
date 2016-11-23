package com.app.theweatherapp.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Beyeta Sanu on 11/19/16.
 */

public class ApiResourceWeatherCondition {
    @SerializedName("fcst_valid")
    private Long time;
    @SerializedName("fcst_valid_local")
    private String timeString;
    @SerializedName("temp")
    private Integer temperature;
    @SerializedName("phrase_22char")
    private String condition;
    @SerializedName("hi")
    private Integer maxTemperature;
    @SerializedName("wc")
    private Integer minTemperature;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Integer maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Integer minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getTimeString() {
        return timeString;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }
}
