package info.jiangchuan.wrca.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.models.Event;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class SharedPrefUtil {
    private static final String TAG = "Utility";


    public static void writeStringSharedPreferences(String key, String val) {
        Context context = WillowRidge.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key ,val);
        editor.commit();
    }
    public static String readStringSharedPreferences(String key) {
        Context context = WillowRidge.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, null);
    }

    public static boolean readBooleanSharedPreferences(String key) {
        Context context = WillowRidge.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, false);
    }

    public static void writeBoolean(String key, boolean b) {
        Context context = WillowRidge.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constant.string_auto_Login, b);
    }

    public static boolean containsEvent(List<Event> list, Event event) {
        for (Event e:list) {
            if (e.getTitle().compareTo(event.getTitle()) == 0) {
                return true;
            }
        }
        return false;
    }
}
