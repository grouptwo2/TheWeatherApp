package com.app.theweatherapp.util;

import android.content.Context;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Locale;

/**
 * Created by Beyeta on 11/19/16.
 */

public class Cache {

    public enum Key {
        SAVED_CITIES
    }

    public static <T> T get(Context context, Key key, T defaultValue) {
        try {
            InputStream inputStream = context.openFileInput(key.name().toLowerCase(Locale.ENGLISH) + ".dat");
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Object o = objectInputStream.readObject();
            objectInputStream.close();
            inputStream.close();
            return (T) o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public static void save(Context context, Key key, Object serializable) {
        try {
            OutputStream outputStream = context.openFileOutput(key.name().toLowerCase(Locale.ENGLISH) + ".dat", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.close();
            objectOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
