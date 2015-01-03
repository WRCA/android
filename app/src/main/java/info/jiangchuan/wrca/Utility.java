package info.jiangchuan.wrca;

import android.content.Context;
import android.widget.Toast;
/**
 * Created by jiangchuan on 1/3/15.
 */
public class Utility {
    public static void showToastMessage(Context context,  CharSequence text, int duration) {
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
