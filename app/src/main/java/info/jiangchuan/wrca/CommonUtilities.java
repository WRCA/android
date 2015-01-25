package info.jiangchuan.wrca;

import android.content.Context;
import android.content.Intent;
/**
 * Created by jiangchuan on 1/24/15.
 */
public class CommonUtilities {
    public static final String SERVER_URL = "http://jiangchuan.info/gcm/register.php";

    // Google project id
    public static final String SENDER_ID = "676405556287";

    /**
     * Tag used on log messages.
     */
    public static final String TAG = "AndroidHive GCM";

    public static final String DISPLAY_MESSAGE_ACTION =
            "com.jiangchuan.pushnotifications.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
