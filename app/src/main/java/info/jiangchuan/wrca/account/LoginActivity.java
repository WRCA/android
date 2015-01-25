package info.jiangchuan.wrca.account;

import static info.jiangchuan.wrca.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static info.jiangchuan.wrca.CommonUtilities.EXTRA_MESSAGE;
import static info.jiangchuan.wrca.CommonUtilities.*;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gcm.GCMRegistrar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.jiangchuan.wrca.AlertDialogManager;
import info.jiangchuan.wrca.ConnectionDetector;
import info.jiangchuan.wrca.Constants;
import info.jiangchuan.wrca.CustomRequest;
import info.jiangchuan.wrca.MainActivity;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.ServerUtilities;
import info.jiangchuan.wrca.WakeLocker;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.util.ToastUtil;
import info.jiangchuan.wrca.util.Utility;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    private LoginActivity mActivity;
    private SharedPreferences mSharePref;

    public static String name;
    public static String email;

    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connection detector
    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;

        if (!hasInternet()) {
            finish();
        }
        if (!isGCMConfigSet()) {
            finish();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        String token = Utility.readStringSharedPreferences(Constants.string_token);
        boolean autoLogin = Utility.readBooleanSharedPreferences(Constants.string_auto_Login);
        boolean hasNotifications = Utility.readBooleanSharedPreferences(Constants.string_notifications);
        if (!hasNotifications) {
            GCMRegistrar.unregister(this);
        }
        if (autoLogin == true && token != "") {
            // start MainActivity
            Intent intent = new Intent(mActivity, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Log.d(TAG, "token is empty");
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
     }
    public void signup(View view) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    public void submit(View view) {
        TextView email = (TextView)findViewById(R.id.edit_text_email);
        TextView password = (TextView)findViewById(R.id.edit_text_password);
        final String strEmail = email.getText().toString().trim();
        final String strPassword = password.getText().toString().trim();
        if (strEmail.length() == 0 || strPassword.length() == 0) {
            ToastUtil.showToastMessage(this, "field cannot be empty", Toast.LENGTH_SHORT);
            return;
        }

        String requestURL = "http://jiangchuan.info/php/index.php";
        Map<String, String> map = new HashMap<String, String>();
        map.put("object", "login");
        map.put("email", strEmail);
        map.put("password", strPassword);
        CustomRequest request = new CustomRequest(Request.Method.POST, requestURL, map,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int result = response.getInt("success");
                            if (result == 0) {
                                ToastUtil.showToastMessage(mActivity, response.get("message").toString(), Toast.LENGTH_LONG);
                            } else if (result == 1) {
                                Log.d(TAG, response.getString("token"));
                                Utility.writeStringSharedPreferences(Constants.string_token, response.getString("token"));
                                Intent intent = new Intent(mActivity, MainActivity.class);
                                mActivity.email = strEmail;
                                mActivity.name = "user";
                                registerGCM();
                                startActivity(intent);
                                finish();
                            } else {
                                ToastUtil.showToastMessage(mActivity, "result code not recognize", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "JSONException");
                            ToastUtil.showToastMessage(mActivity, e.toString(), Toast.LENGTH_LONG);
                        }
                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse");
                        Toast toast =  Toast.makeText(mActivity, error.toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                });

        WillowRidge.getInstance().getRequestQueue().add(request);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    public boolean hasInternet() {
        cd = new ConnectionDetector(getApplicationContext());

        // Check if Internet present
        if (!cd.isConnectingToInternet()) {
            // Internet Connection is not present
            alert.showAlertDialog(this,
                    "Internet Connection Error",
                    "Please connect to working Internet connection", false);
            // stop executing code by return
            return false;
        }
        return true;
    }

    public void registerGCM() {
        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(mActivity);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(mActivity);


        registerReceiver(mHandleMessageReceiver, new IntentFilter(
                DISPLAY_MESSAGE_ACTION));

        // Get GCM registration id
        final String regId = GCMRegistrar.getRegistrationId(mActivity);

        // Check if regid already presents
        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(mActivity, SENDER_ID);
            Log.d("GCM", "register on google");
        } else {
            // Device is already registered on GCM
            if (GCMRegistrar.isRegisteredOnServer(mActivity)) {
                // Skips registration.
            } else {
                // Try to register again, but not in the UI thread.
                // It's also necessary to cancel the thread onDestroy(),
                // hence the use of AsyncTask instead of a raw thread.
                final Context context = mActivity;
                mRegisterTask = new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        // Register on our server
                        // On server creates a new user
                        Log.d("GCM", "lets register");
                        ServerUtilities.register(context, name, email, regId);
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

    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());

            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

    private boolean isGCMConfigSet() {
        // Check if GCM configuration is set
        if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
                || SENDER_ID.length() == 0) {
            // GCM sernder id / server url is missing
            alert.showAlertDialog(this, "Configuration Error!",
                    "Please set your Server URL and GCM Sender ID", false);
            // stop executing code by return
            return false;
        }
        return true;
    }
}
