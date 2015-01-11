package info.jiangchuan.wrca;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.widget.Toast;
import android.app.Activity;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import android.util.Log;
/**
 * Created by jiangchuan on 1/3/15.
 */
public class Utility {
    private static final String TAG = "Utility";
    public static void showToastMessage(Context context,  CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static void writeSavedEventsToFile(ArrayList<Event> list) {
        try {
            FileOutputStream fos = WRCAApplication.getInstance().openFileOutput(Constants.string_saved_file, Context.MODE_PRIVATE);
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
            FileInputStream fis = WRCAApplication.getInstance().openFileInput(Constants.string_saved_file);
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
        Context context = WRCAApplication.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key ,val);
        editor.commit();
    }
    public static String readStringSharedPreferences(String key) {
        Context context = WRCAApplication.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getString(key, null);
    }

    public static boolean readBooleanSharedPreferences(String key) {
        Context context = WRCAApplication.getInstance();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        return pref.getBoolean(key, false);
    }
}
