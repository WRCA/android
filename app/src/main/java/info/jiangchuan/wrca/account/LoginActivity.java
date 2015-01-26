package info.jiangchuan.wrca.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.MainActivity;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.gcm.AlertDialogManager;
import info.jiangchuan.wrca.gcm.ConnectionDetector;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

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
    }

    @Override
    public void onStart() {
        super.onStart();

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
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", strEmail);
        map.put("password", strPassword);
        Client.getApi().login(map, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                String msg = jsonObject.toString();
                Log.d(TAG, jsonObject.toString());
                Result result = ResultParser.parse(jsonObject);
                switch (result.getStatus()) {
                    case 200: {
                        String token = jsonObject.get(Constant.AUTH_TOKEN).getAsString();
                        SharedPrefUtil.writeStringSharedPreferences(Constant.AUTH_TOKEN, token);
                        User user = new User();
                        user.setEmail(strEmail);
                        user.setPassword(strPassword);
                        user.setToken(token);
                        Log.i(TAG, "user info was put to intent");
                        setResult(RESULT_OK, getIntent().putExtra("User", user));
                        finish();
                        break;
                    }
                    default: {
                        ToastUtil.showToastMessage(mActivity, result.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
