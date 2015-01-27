package info.jiangchuan.wrca.account;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.MainActivity;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.dialogs.ForgetPassDialog;
import info.jiangchuan.wrca.gcm.AlertDialogManager;
import info.jiangchuan.wrca.gcm.ConnectionDetector;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.rest.RestConst;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
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
                        String token = jsonObject.get(RestConst.REQ_PARAM_TOKEN).getAsString();
                        SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, token);
                        WillowRidge.getInstance().getUser().setToken(token);
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    default: {
                        ToastUtil.showToastMessage(getApplication(), result.getMessage());
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

    public void forgetPassword(View view) {
        final Dialog dialog = new ForgetPassDialog(this);
        dialog.show();
    }
}
