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
import info.jiangchuan.wrca.dialogs.OpenWifiSettingDialog;
import info.jiangchuan.wrca.gcm.AlertDialogManager;
import info.jiangchuan.wrca.gcm.ConnectionDetector;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.rest.RestConst;
import info.jiangchuan.wrca.util.DialogUtil;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;


public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    private SharedPreferences mSharePref;

    public static TextView txtEmail;
    public static TextView txtPassword;


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
        txtEmail = (TextView)findViewById(R.id.edit_text_email);
        txtPassword = (TextView)findViewById(R.id.edit_text_password);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (NetworkUtil.hasInternet(this) == false) {
            Dialog dialog = new OpenWifiSettingDialog(this);
            dialog.show();
        }
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
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            ToastUtil.showToast(this, "field cannot be empty", Toast.LENGTH_SHORT);
            return;
        }

        if (NetworkUtil.hasInternet(this) == false) {
           ToastUtil.showToast(this, R.string.toast_system_offline);
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put(RestConst.REQ_PARAM_EMAIL, email);
        map.put(RestConst.REQ_PARAM_PASS, password);
        DialogUtil.setup(this);
        DialogUtil.showProgressDialog("Waiting...");
        Client.getApi().login(map, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                DialogUtil.hideProgressDialog();
                String msg = jsonObject.toString();
                Log.d(TAG, jsonObject.toString());
                Result result = ResultParser.parse(jsonObject);
                switch (result.getStatus()) {
                    case RestConst.INT_STATUS_200: {
                        String token = jsonObject.get(RestConst.REQ_PARAM_TOKEN).getAsString();
                        SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, token);
                        WillowRidge.getInstance().getUser().setToken(token);
                        Intent intent = new Intent(getApplication(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    default: {
                        ToastUtil.showToast(getApplication(), result.getMessage());
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                DialogUtil.hideProgressDialog();
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
