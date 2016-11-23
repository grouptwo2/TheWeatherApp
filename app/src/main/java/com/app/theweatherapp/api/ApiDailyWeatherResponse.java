package com.app.theweatherapp.api;

/**
 * Created by Beyeta Sanu
 * <p>
 * on 11/19/16.
 */

public class ApiDailyWeatherResponse {
    private ApiMetadata metadata;
    private ApiObservation observation;
    private ApiDailyWeather[] forecasts;

    public ApiMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ApiMetadata metadata) {
        this.metadata = metadata;
    }

    public ApiObservation getObservation() {
        return observation;
    }

    public void setObservation(ApiObservation observation) {
        this.observation = observation;
    }

    public ApiDailyWeather[] getForecasts() {
        return forecasts;
    }

    public void setForecasts(ApiDailyWeather[] forecasts) {
        this.forecasts = forecasts;
    }
}
