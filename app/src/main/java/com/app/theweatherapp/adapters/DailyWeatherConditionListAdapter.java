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
import com.app.theweatherapp.api.ApiDailyWeather;
import com.app.theweatherapp.api.ApiResourceWeatherCondition;
import com.app.theweatherapp.util.WeatherConditionUtil;

import java.util.List;
import java.util.Locale;

/**
 * Created by beyeta on 11/19/16.
 */

public class DailyWeatherConditionListAdapter extends ArrayAdapter<ApiDailyWeather> {

    public DailyWeatherConditionListAdapter(Context context, int resource, List<ApiDailyWeather> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_daily_forcast, null);
        }

        ApiResourceWeatherCondition weatherCondition = getItem(position).getNight();

        if (weatherCondition != null) {
            TextView timeTextView = (TextView) convertView.findViewById(R.id.time);
            TextView conditionTextView = (TextView) convertView.findViewById(R.id.condition);
            TextView temperatureTextView = (TextView) convertView.findViewById(R.id.temperature);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);

            timeTextView.setText(WeatherConditionUtil.format(weatherCondition.getTimeString(), "hh:mm a"));
            conditionTextView.setText(weatherCondition.getCondition());
            temperatureTextView.setText(String.format(Locale.ENGLISH, "%d °F", weatherCondition.getTemperature()));
            imageView.setImageResource(WeatherConditionUtil.getConditionIcon(weatherCondition.getCondition()));
        }

        weatherCondition = getItem(position).getDay();

        if (weatherCondition != null) {
            TextView timeTextView = (TextView) convertView.findViewById(R.id.dayTime);
            TextView conditionTextView = (TextView) convertView.findViewById(R.id.dayCondition);
            TextView temperatureTextView = (TextView) convertView.findViewById(R.id.dayTemperature);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.dayIcon);

            timeTextView.setText(WeatherConditionUtil.format(weatherCondition.getTimeString(), "hh:mm a"));
            conditionTextView.setText(weatherCondition.getCondition());
            temperatureTextView.setText(String.format(Locale.ENGLISH, "%d °F", weatherCondition.getTemperature()));
            imageView.setImageResource(WeatherConditionUtil.getConditionIcon(weatherCondition.getCondition()));
        }

        TextView dayTextView = (TextView) convertView.findViewById(R.id.mainTime);
        TextView tempTextView = (TextView) convertView.findViewById(R.id.mainTemp);

        dayTextView.setText(WeatherConditionUtil.format(getItem(position).getTimeString(), "EEEE dd, MMM"));
        tempTextView.setText(String.format(Locale.ENGLISH, "%d °F / %d °F", getItem(position).getMinimumTemperature(), getItem(position).getMaximumTemperature()));
        return convertView;
    }
}
