package info.jiangchuan.wrca.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import info.jiangchuan.wrca.models.User;

import static info.jiangchuan.wrca.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static info.jiangchuan.wrca.gcm.CommonUtilities.EXTRA_MESSAGE;
import static info.jiangchuan.wrca.gcm.CommonUtilities.SENDER_ID;
import static info.jiangchuan.wrca.gcm.CommonUtilities.SERVER_URL;

import com.google.android.gcm.GCMRegistrar;
/**
 * Created by jiangchuan on 1/26/15.
 */
public class GCMService {
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    Context context = null;
    public GCMService(Context context) {
        this.context = context;
    }

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();
     /**
     * Receiving push messages
     * */

     private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(context.getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            Toast.makeText(context.getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };


    public void register(User u) {
        // Make sure the device has the proper dependencies.
        final  User user = u;
        GCMRegistrar.checkDevice(context);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(context);


        context.registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(context);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(context, SENDER_ID);
            Log.d("GCM", "register on google");
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(context)) {
                // Skips registration.
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = this.context;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        Log.d("GCM", "lets register");
                        ServerUtilities.register(context, user.getName(), user.getEmail(), regId);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void result) {
                        mRegisterTask = null;
                    }

                };
                mRegisterTask.execute(null, null, null);
            }
        }
    }

    public void unRegister() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            context.unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.unregister(context);
            GCMRegistrar.onDestroy(context);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
    }
    public boolean isGCMConfigSet() {
        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(context, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return false;
        }
        return true;
    }
}
