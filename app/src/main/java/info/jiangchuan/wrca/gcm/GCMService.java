package info.jiangchuan.wrca.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.models.User;

import static info.jiangchuan.wrca.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static info.jiangchuan.wrca.gcm.CommonUtilities.EXTRA_MESSAGE;
import static info.jiangchuan.wrca.gcm.CommonUtilities.SENDER_ID;
import static info.jiangchuan.wrca.gcm.CommonUtilities.SERVER_URL;
/**
 * Created by jiangchuan on 1/26/15.
 */
public class GCMService {

    Context context = null;

    private boolean registered = false;

    public boolean isRegistered() {
        return registered;
    }


    public GCMService(Context context) {
        this.context = context;
    }

     /**
     * Receiving push messages
     * */

     private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(context.getApplicationContext());


            // Releasing wake lock
            WakeLocker.release();
        }
    };


    public void register() {
        User user = WillowRidge.getInstance().getUser();
        GCMRegistrar.checkDevice(context);
        GCMRegistrar.checkManifest(context);
        context.registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(context);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(context, SENDER_ID);

            if (Constant.DEBUG)
                Log.d("GCM", "register on google");
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(context)) {
                // Skips registration.
            } else {
                ServerUtilities.register(this.context, user.getName(), user.getEmail(), regId);
            }
            registered = true;
        }
    }

    public void unRegister() {
        try {
            context.unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.unregister(context);
            GCMRegistrar.onDestroy(context);
            registered = false;
        } catch (Exception e) {
            if (Constant.DEBUG)
                Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
    }
    public boolean isGCMConfigSet() {
        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            return false;
        }
        return true;
    }
}
