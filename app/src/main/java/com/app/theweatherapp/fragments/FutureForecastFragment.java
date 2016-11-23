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
import com.app.theweatherapp.adapters.DailyWeatherConditionListAdapter;
import com.app.theweatherapp.adapters.HourlyWeatherConditionListAdapter;
import com.app.theweatherapp.api.ApiDailyWeather;
import com.app.theweatherapp.api.ApiDailyWeatherResponse;
import com.app.theweatherapp.api.ApiHourlyWeatherResponse;
import com.app.theweatherapp.api.ApiResourceCity;
import com.app.theweatherapp.util.JSONUtil;
import com.noubug.lib.nouhttp.NouHTTP;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class FutureForecastFragment extends Fragment {

    private List<ApiDailyWeather> dailyWeathers = new ArrayList<>();
    private DailyWeatherConditionListAdapter adapter;
    private Context context;
    private ApiResourceCity city;

    public FutureForecastFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_future_forecast, container, false);
        setUpView(view);
        loadForecasts();
        return view;
    }

    private void loadForecasts() {
        NouHTTP.with(context).get(String.format(Locale.ENGLISH, "https://twcservice.mybluemix.net/api/weather/v1/geocode/%f/%f/forecast/daily/10day.json", city.getLatitude(), city.getLongitude()))
                .inTheFuture(new NouHTTP.Future() {
                    @Override
                    public void completed(Exception e, int statusCode, String t) {
                        if (statusCode == 200 && e == null) {
                            ApiDailyWeatherResponse weatherResponse = JSONUtil.fromJSON(t, ApiDailyWeatherResponse.class);
                            dailyWeathers.clear();
                            Collections.addAll(dailyWeathers, weatherResponse.getForecasts());
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Could not load hourly forecasts", Toast.LENGTH_LONG).show();
                        }
                    }
                }).nou();
    }

    private void setUpView(View view) {
        ListView listView = (ListView) view.findViewById(R.id.listView);
        adapter = new DailyWeatherConditionListAdapter(context, R.layout.list_item_weather_forecast, dailyWeathers);
        listView.setAdapter(adapter);
    }
}
