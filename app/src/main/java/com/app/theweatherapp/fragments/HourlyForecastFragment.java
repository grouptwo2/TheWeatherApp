package com.app.theweatherapp.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.theweatherapp.R;
import com.app.theweatherapp.activities.CityWeatherForecastDetailActivity;
import com.app.theweatherapp.adapters.HourlyWeatherConditionListAdapter;
import com.app.theweatherapp.api.ApiResourceCity;
import com.app.theweatherapp.api.ApiResourceWeatherCondition;
import com.app.theweatherapp.api.ApiHourlyWeatherResponse;
import com.app.theweatherapp.util.JSONUtil;
import com.noubug.lib.nouhttp.NouHTTP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HourlyForecastFragment extends Fragment {

    private Context context;
    private ApiResourceCity city;
    private List<ApiResourceWeatherCondition> weatherConditions = new ArrayList<>();
    private HourlyWeatherConditionListAdapter adapter;


    public HourlyForecastFragment() {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hourly_forecast, container, false);
        setUpView(view);
        loadForecasts();
        return view;
    }

    private void loadForecasts() {
        NouHTTP.with(context).get(String.format(Locale.ENGLISH, "https://twcservice.mybluemix.net/api/weather/v1/geocode/%f/%f/forecast/hourly/48hour.json", city.getLatitude(), city.getLongitude()))
                .inTheFuture(new NouHTTP.Future() {
                    @Override
                    public void completed(Exception e, int statusCode, String t) {
                        if (statusCode == 200 && e == null) {
                            ApiHourlyWeatherResponse weatherResponse = JSONUtil.fromJSON(t, ApiHourlyWeatherResponse.class);
                            weatherConditions.clear();
                            Collections.addAll(weatherConditions, weatherResponse.getForecasts());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Could not load hourly forecasts", Toast.LENGTH_LONG).show();
                        }
                    }
                }).nou();
    }

    private void setUpView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new HourlyWeatherConditionListAdapter(context, R.layout.list_item_weather_forecast, weatherConditions, "HH:mm a");
        listView.setAdapter(adapter);
    }


}
