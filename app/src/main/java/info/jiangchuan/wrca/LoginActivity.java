package info.jiangchuan.wrca;

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

public class LoginActivity extends ActionBarActivity {

    private static final String TAG = "LoginActivity";
    private LoginActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mActivity = this;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        if (sharedPref == null) {
            Log.e(TAG, "get SharedPreferences handle err");
        }

        String token =  sharedPref.getString(Constants.string_token, null);
        if (token != null) {
           // start MainActivity
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
                        Log.d(TAG, "onResponse");
                        try {
                            int result = response.getInt("success");
                            if (result == 0) {
                                Utility.showToastMessage(mActivity, response.toString(), Toast.LENGTH_LONG);
                            } else if (result == 1) {
                                Utility.showToastMessage(mActivity, "login success", Toast.LENGTH_LONG);

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
}
