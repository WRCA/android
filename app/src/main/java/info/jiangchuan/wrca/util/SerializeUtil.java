package info.jiangchuan.wrca.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.WillowRidge;

/**
 * Created by jiangchuan on 1/25/15.
 */
/*
 * serialize object to file
 */
public class SerializeUtil {
    static final String TAG = "SerializeUtil";

    // to file
    public static void serialize(String name, Object object) {
        try {
            File traceFile = new File((WillowRidge.getInstance()).getExternalFilesDir(null), name);
            if (!traceFile.exists()) {
                traceFile.createNewFile();
            }

            PrintWriter writer = new PrintWriter(traceFile);
            writer.print("");
            writer.close();

            FileOutputStream fos= new FileOutputStream(traceFile);
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
            fos.close();
        } catch (Exception e) {
            if (Constant.DEBUG)
                Log.d(TAG, e.toString());
        }
    }

    public static Object deSerialize(String name) {
        try {
            File traceFile = new File((WillowRidge.getInstance()).getExternalFilesDir(null), name);
            if (!traceFile.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(traceFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object object =  ois.readObject();
            ois.close();
            fis.close();
            return object;
        } catch (Exception e) {
            if (Constant.DEBUG)
                Log.d(TAG, e.toString());
            return null;
        }

    }
}
