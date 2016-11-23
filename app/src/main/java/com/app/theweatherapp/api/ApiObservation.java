package com.app.theweatherapp.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Beyeta on 11/12/16.
 */

public class ApiObservation {
    @SerializedName("min_temp")
    private int minTemperature;
    @SerializedName("temp")
    private int currentTemperature;
    @SerializedName("max_temp")
    private int maxTemperature;
    @SerializedName("wx_phrase")
    private String condition;
    @SerializedName("pressure")
    private Double pressure = 0D;
    @SerializedName("wdir_cardinal")
    private String windCardinal = "";
    @SerializedName("feels_like")
    private int feelsLike = 0;
    @SerializedName("heat_index")
    private int heatIndex = 0;

    public int getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(int minTemperature) {
        this.minTemperature = minTemperature;
    }

    public int getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(int currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public int getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(int maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public String getWindCardinal() {
        return windCardinal;
    }

    public void setWindCardinal(String windCardinal) {
        this.windCardinal = windCardinal;
    }

    public int getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(int feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getHeatIndex() {
        return heatIndex;
    }

    public void setHeatIndex(int heatIndex) {
        this.heatIndex = heatIndex;
    }
}
