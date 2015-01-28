package info.jiangchuan.wrca.account;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
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
import info.jiangchuan.wrca.dialogs.GetVeriCodeDialog;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.rest.RestConst;
import info.jiangchuan.wrca.util.DialogUtil;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

public class SignupActivity extends ActionBarActivity {

    private static final String TAG = "SignupActivity";
    private SignupActivity mActivity;

    TextView txtEmail;
    TextView txtPassword;
    TextView txtVericode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mActivity = this;
        DialogUtil.setup(this);
        txtEmail = (TextView)findViewById(R.id.edit_text_email);
        txtPassword = (TextView)findViewById(R.id.edit_text_password);
        txtVericode = (TextView)findViewById(R.id.edit_text_verification_code);
    }

    public void onSubmit(View view) {
        final String email = txtEmail.getText().toString().trim();
        final String password = txtPassword.getText().toString().trim();
        final String vericode = txtVericode.getText().toString().trim();

        if (TextUtils.isEmpty(vericode)) {
            ToastUtil.showToast(this, "To get verification Code, please click Get Verification Code Button");
            return;
        }

        if (TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(vericode)) {
            ToastUtil.showToast(this, "field cannot be empty");
            return;
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put(RestConst.REQ_PARAM_EMAIL, email);
        map.put(RestConst.REQ_PARAM_PASS, password);
        map.put(RestConst.REQ_PARAM_VERICODE, vericode);
        DialogUtil.showProgressDialog("waiting...");
        Client.getApi().register(map, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                DialogUtil.hideProgressDialog();
                Result result = ResultParser.parse(jsonObject);
                switch (response.getStatus()) {
                    case RestConst.INT_STATUS_200: {
                        String token = jsonObject.get(Constant.STRING_AUTH_TOKEN).toString();
                        SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, token);
                        Intent intent = new Intent(mActivity, MainActivity.class);
                        User user = WillowRidge.getInstance().getUser();
                        user.setEmail(email);
                        user.setToken(token);
                        user.setNotification(true); // new user, set push notificaion to true
                        WillowRidge.getInstance().getGcmService().register(user);
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
    public void onGetVerificationCode(View view) {
        Dialog dialog = new GetVeriCodeDialog(this);
        dialog.show();
    }
    public void onBack(View view) {
        finish();
    }
}
