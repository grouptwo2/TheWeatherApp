package com.app.theweatherapp.util;

import com.app.theweatherapp.R;

import java.util.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by emmanuel on 11/19/16.
 */

public class WeatherConditionUtil {
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();

    public static String format(Long timeStamp, String format) {
        SIMPLE_DATE_FORMAT.applyPattern(format);
        return SIMPLE_DATE_FORMAT.format(new Timestamp(timeStamp));
    }

    public static String format(String timeStamp, String format) {
        SIMPLE_DATE_FORMAT.applyPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = SIMPLE_DATE_FORMAT.parse(timeStamp);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        SIMPLE_DATE_FORMAT.applyPattern(format);
        return SIMPLE_DATE_FORMAT.format(new Timestamp(date.getTime()));
    }

    public static int getConditionIcon(String condition) {
        condition = condition.toLowerCase();
        if (condition.contains("sunny")) {
            return R.drawable.sun_cloud;
        } else if (condition.contains("cloudy")) {
            return R.drawable.few_cloud_no_sun;
        } else if (condition.contains("rain")) {
            return R.drawable.few_rain;
        } else if (condition.contains("snow")) {
            return R.drawable.cloud_snow;
        } else if (condition.contains("showers")) {
            return R.drawable.few_rain;
        } else if (condition.contains("storm")) {
            return R.drawable.foggy;
        } else if (condition.contains("wind")) {
            return R.drawable.foggy;
        } else {
            return R.drawable.few_cloud_no_sun;
        }
    }
}
