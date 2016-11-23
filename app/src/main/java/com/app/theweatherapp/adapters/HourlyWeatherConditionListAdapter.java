package com.app.theweatherapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.theweatherapp.R;
import com.app.theweatherapp.api.ApiResourceWeatherCondition;
import com.app.theweatherapp.util.WeatherConditionUtil;

import java.util.List;
import java.util.Locale;

/**
 * Created by beyeta on 11/19/16.
 */

public class HourlyWeatherConditionListAdapter extends ArrayAdapter<ApiResourceWeatherCondition> {
    private String timeFormat;

    public HourlyWeatherConditionListAdapter(Context context, int resource, List<ApiResourceWeatherCondition> objects, String timeFormat) {
        super(context, resource, objects);
        this.timeFormat = timeFormat;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_weather_forecast, null);
        }

        ApiResourceWeatherCondition weatherCondition = getItem(position);

        TextView timeTextView = (TextView) convertView.findViewById(R.id.time);
        TextView conditionTextView = (TextView) convertView.findViewById(R.id.condition);
        TextView temperatureTextView = (TextView) convertView.findViewById(R.id.temperature);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);

        timeTextView.setText(WeatherConditionUtil.format(weatherCondition.getTimeString(), "hh:mm a"));
        conditionTextView.setText(weatherCondition.getCondition());
        temperatureTextView.setText(String.format(Locale.ENGLISH, "%d °F", weatherCondition.getTemperature()));
        imageView.setImageResource(WeatherConditionUtil.getConditionIcon(weatherCondition.getCondition()));
        return convertView;
    }
}