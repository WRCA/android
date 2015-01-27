package info.jiangchuan.wrca.util;

import android.content.Context;
import android.widget.Toast;
import android.content.Context;
/**
 * Created by jiangchuan on 1/25/15.
 */
public class ToastUtil {

    public static void showToast(Context context, String text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public static void showToast(Context context, String text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(Context context, int stringResId) {
        showToast(context, context.getString(stringResId));
    }
}
