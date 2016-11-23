package com.app.theweatherapp.api;

/**
 * Created by Beyeta on 11/12/16.
 */

public class ApiHourlyWeatherResponse {
    private ApiMetadata metadata;
    private ApiObservation observation;
    private ApiResourceWeatherCondition[] forecasts;

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

    public ApiResourceWeatherCondition[] getForecasts() {
        return forecasts;
    }

    public void setForecasts(ApiResourceWeatherCondition[] forecasts) {
        this.forecasts = forecasts;
    }
}
