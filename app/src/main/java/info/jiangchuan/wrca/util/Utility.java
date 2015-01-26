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
public class Utility {
    private static final String TAG = "Utility";

    public static void writeSavedEventsToFile(ArrayList<Event> list) {
        try {
            FileOutputStream fos = WillowRidge.getInstance().openFileOutput(Constant.string_saved_file, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            fos.close();
            oos.close();
        } catch (IOException e) {
           Log.d(TAG, e.toString());
        }
    }

    public static List<Event> readSavedEventsFromFile() {
        try {
            FileInputStream fis = WillowRidge.getInstance().openFileInput(Constant.string_saved_file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Event> list = (List<Event>)ois.readObject();
            fis.close();
            ois.close();
            return list;
        } catch (Exception e) {
            Log.d(TAG, e.toString());
            return null;
        }
    }

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
