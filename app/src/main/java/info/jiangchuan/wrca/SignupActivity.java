package info.jiangchuan.wrca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import info.jiangchuan.wrca.util.ToastUtil;

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

        String requestURL = "http://jiangchuan.info/php/index.php";
        Map<String, String> map = new HashMap<String, String>();
        map.put("object", "register");
        map.put("email", strEmail);
        map.put("password", strPassword);
        map.put("verificationCode", strVerificationCode);
        CustomRequest request = new CustomRequest(Request.Method.POST, requestURL, map,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse");

                        try {
                            int result = response.getInt("success");
                            if (result == 0) {
                                ToastUtil.showToastMessage(mActivity, response.toString(), Toast.LENGTH_LONG);
                            } else if (result == 1) {
                                ToastUtil.showToastMessage(mActivity, "login success", Toast.LENGTH_LONG);

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
                        Toast.makeText(mActivity, error.toString(), Toast.LENGTH_LONG);
                    }
                });

        WillowRidge.getInstance().getRequestQueue().add(request);
    }
    public void onGetVerificationCode(View view) {
        TextView email = (TextView)findViewById(R.id.edit_text_email);
        final TextView password = (TextView)findViewById(R.id.edit_text_password);
        final String strEmail = email.getText().toString().trim();
        if (strEmail.length() == 0) {
            ToastUtil.showToastMessage(this, "email field cannot be empty", Toast.LENGTH_SHORT);
            return;
        }

        String requestURL = "http://jiangchuan.info/php/index.php?object=verificationCode&email="+strEmail;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse");
                        try {
                            int result = response.getInt("success");
                            if (result == 0) {
                                ToastUtil.showToastMessage(mActivity, response.toString(), Toast.LENGTH_LONG);
                            } else if (result == 1) {
                                ToastUtil.showToastMessage(mActivity, response.toString(), Toast.LENGTH_LONG);

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
                        Toast.makeText(mActivity, error.toString(), Toast.LENGTH_LONG);
                    }
                });

        WillowRidge.getInstance().getRequestQueue().add(request);
    }
    public void onBack(View view) {
        finish();
    }
}
