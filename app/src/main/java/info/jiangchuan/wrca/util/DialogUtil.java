package info.jiangchuan.wrca.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by jiangchuan on 1/27/15.
 */
public class DialogUtil {

    static ProgressDialog dialog;
    public static void setup(Context context) {
       dialog = new ProgressDialog(context);
    }
    public static void showProgressDialog(String message) {
        dialog.setMessage(message);
        dialog.show();
    }

    public static void hideProgressDialog() {
       dialog.dismiss();
    }
}
