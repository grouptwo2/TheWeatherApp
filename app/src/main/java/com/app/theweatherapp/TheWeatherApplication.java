package com.app.theweatherapp;

import android.app.Application;

import com.app.theweatherapp.util.Preference;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Beyeta Sanu
 * <p>
 * on 11/19/16.
 */

public class TheWeatherApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/HelveticaNeueLTPro-Th.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Preference.get(TheWeatherApplication.this, "username", getString(R.string.username)), Preference.get(TheWeatherApplication.this, "password", getString(R.string.password)).toCharArray());
            }
        });
    }
}
