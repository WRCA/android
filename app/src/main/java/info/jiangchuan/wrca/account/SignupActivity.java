package info.jiangchuan.wrca.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
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
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

public class SignupActivity extends ActionBarActivity {

    private static final String TAG = "SignupActivity";
    private SignupActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mActivity = this;
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onSubmit(View view) {
        TextView email = (TextView)findViewById(R.id.edit_text_email);
        TextView password = (TextView)findViewById(R.id.edit_text_password);
        TextView verificationCode = (TextView)findViewById(R.id.edit_text_verification_code);
        final String strEmail = email.getText().toString().trim();
        final String strPassword = password.getText().toString().trim();
        final String strVerificationCode = verificationCode.getText().toString().trim();

        if (strVerificationCode.length() == 0) {
            ToastUtil.showToastMessage(this, "to get verfication code, press button", Toast.LENGTH_SHORT);
            return;
        }

        if (strEmail.length() == 0 || strPassword.length() == 0 || strVerificationCode.length() == 0) {
            ToastUtil.showToastMessage(this, "field cannot be empty", Toast.LENGTH_SHORT);
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("email", strEmail);
        map.put("password", strPassword);
        map.put("verificationCode", strVerificationCode);
        Client.getApi().register(map, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
               Result result = ResultParser.parse(jsonObject);
               switch (response.getStatus()) {
                   case 200: {
                       String token = jsonObject.get(Constant.STRING_AUTH_TOKEN).toString();
                       SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, token);
                       Intent intent = new Intent(mActivity, MainActivity.class);
                       User user = MainActivity.getActivity().getUserData().getUser();
                       user.setEmail(strEmail);
                       user.setPassword(strPassword);
                       user.setToken(token);
                       WillowRidge.getInstance().getGcmService().register(user);
                       startActivity(intent);
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
    public void onGetVerificationCode(View view) {
        TextView email = (TextView)findViewById(R.id.edit_text_email);
        final TextView password = (TextView)findViewById(R.id.edit_text_password);
        final String strEmail = email.getText().toString().trim();
        if (strEmail.length() == 0) {
            ToastUtil.showToastMessage(this, "email field cannot be empty", Toast.LENGTH_SHORT);
            return;
        }
        Client.getApi().vericode(strEmail, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                Result result = ResultParser.parse(jsonObject);
                switch (result.getStatus()) {
                    case 200: {
                        ToastUtil.showToastMessage(mActivity, result.getMessage());
                        break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public void onBack(View view) {
        finish();
    }
}
