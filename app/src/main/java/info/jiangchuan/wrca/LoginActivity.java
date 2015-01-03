package info.jiangchuan.wrca;

import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.HashMap;

import android.content.Intent;

import junit.framework.Assert;

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    private LoginActivity mActivity;
    private SharedPreferences mSharePref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;
        mSharePref = getPreferences(Context.MODE_PRIVATE);

        String token = sharedPreferencesReadKeyValue(Constants.string_token);
        if (token != "") {
           // start MainActivity
            Intent intent = new Intent(mActivity, MainActivity.class);
            startActivity(intent);
        } else {
            Log.d(TAG, "token is empty");
        }
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
            Utility.showToastMessage(this, "field cannot be empty", Toast.LENGTH_SHORT);
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
                                Utility.showToastMessage(mActivity, response.toString(), Toast.LENGTH_LONG);
                            } else if (result == 1) {
                                Log.d(TAG, response.getString("token"));
                                sharedPreferencesWriteKeyPair(Constants.string_token, response.getString("token"));
                                Intent intent = new Intent(mActivity, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Utility.showToastMessage(mActivity, "result code not recognize", Toast.LENGTH_LONG);
                            }
                        } catch (JSONException e) {
                            Log.e(TAG, "JSONException");
                            Utility.showToastMessage(mActivity, e.toString(), Toast.LENGTH_LONG);
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

        WRCAApplication.getInstance().getRequestQueue().add(request);
    }

    private SharedPreferences getSharedPreference() {
       if (mSharePref == null) {
       }
        return mSharePref;
    }
    private void sharedPreferencesWriteKeyPair (String key, String value) {
        if (mSharePref == null) {
            Log.e(TAG, "getSharedPreference null error");
            return;
        }
        SharedPreferences.Editor editor = mSharePref.edit();
        editor.putString(key, value); // save the token
        editor.commit();
    }
    private String sharedPreferencesReadKeyValue(String key) {
        if (mSharePref == null) {
            Log.e(TAG, "getSharedPreference null error");
            return null;
        }
        return mSharePref.getString(key, null);
    }
}
