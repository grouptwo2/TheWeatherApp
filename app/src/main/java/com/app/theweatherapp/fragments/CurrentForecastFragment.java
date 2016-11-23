package com.app.theweatherapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.theweatherapp.R;
import com.app.theweatherapp.activities.CityWeatherForecastDetailActivity;
import com.app.theweatherapp.api.ApiDailyWeatherResponse;
import com.app.theweatherapp.api.ApiHourlyWeatherResponse;
import com.app.theweatherapp.api.ApiResourceCity;
import com.app.theweatherapp.api.ApiResourceWeatherCondition;
import com.app.theweatherapp.util.JSONUtil;
import com.noubug.lib.nouhttp.NouHTTP;

import java.util.Collections;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentForecastFragment extends Fragment {

    private ApiResourceCity city;
    private Context context;
    private TextView temperature;
    private TextView condition;
    private TextView pressure;
    private TextView wind;
    private TextView feelsLike;
    private TextView heatIndex;

    public CurrentForecastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        this.city = ((CityWeatherForecastDetailActivity) context).getCity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_observation, container, false);
        // Inflate the layout for this fragment
        init(view);
        loadCurrentCondition();
        return view;
    }

    private void init(View view) {
        temperature = (TextView) view.findViewById(R.id.temperature);
        condition = (TextView) view.findViewById(R.id.condition);
        pressure = (TextView) view.findViewById(R.id.pressure);
        wind = (TextView) view.findViewById(R.id.wind);
        feelsLike = (TextView) view.findViewById(R.id.feelsLike);
        heatIndex = (TextView) view.findViewById(R.id.heatIndex);
    }

    private void loadCurrentCondition() {
        NouHTTP.enableLogging();
        NouHTTP.with(context).get(String.format(Locale.ENGLISH, "https://twcservice.mybluemix.net/api/weather/v1/geocode/%f/%f/observations.json", city.getLatitude(), city.getLongitude()))
                .addParam("language", "en-US")
                .inTheFuture(new NouHTTP.Future() {
                    @Override
                    public void completed(Exception e, int statusCode, String t) {
                        if (statusCode == 200 && e == null) {
                            ApiHourlyWeatherResponse weatherResponse = JSONUtil.fromJSON(t, ApiHourlyWeatherResponse.class);
                            update(weatherResponse);
                        } else {
                            Toast.makeText(context, "Could not load current forecasts", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).nou();
    }

    private void update(ApiHourlyWeatherResponse weatherResponse) {
        if (weatherResponse != null && weatherResponse.getObservation() != null) {
            heatIndex.setText(String.format("%s", weatherResponse.getObservation().getHeatIndex()));
            feelsLike.setText(String.format("%s", weatherResponse.getObservation().getFeelsLike()));
            condition.setText(weatherResponse.getObservation().getCondition());
            pressure.setText(String.format("%s", weatherResponse.getObservation().getPressure()));
            wind.setText(weatherResponse.getObservation().getWindCardinal());
            temperature.setText(String.format("%s °F", weatherResponse.getObservation().getCurrentTemperature()));
        }
    }

}
