package info.jiangchuan.wrca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import info.jiangchuan.wrca.account.LoginActivity;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.SerializeUtil;
import info.jiangchuan.wrca.util.SharedPrefUtil;

/**
 * Created by jiangchuan on 1/26/15.
 */

/*
 * work in this class is to check system info and launch a specified activity (login or main)
 */
public class LauncherActivity extends Activity{
    private static final String TAG = "LauncherActivity";

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // check network connection
        if (!NetworkUtil.hasInternet(this)) {
            finish();
        }
        // check gcm service
        if (!WillowRidge.getInstance().getGcmService().isGCMConfigSet()) {
            finish();
        }

        String token = SharedPrefUtil.readStringSharedPreferences(Constant.AUTH_TOKEN);
        boolean autoLogin = SharedPrefUtil.readBooleanSharedPreferences(Constant.string_auto_Login);
        boolean hasNotifications = SharedPrefUtil.readBooleanSharedPreferences(Constant.string_notifications);
        if (autoLogin == true && TextUtils.isEmpty(token) == false) {
            // start MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivityForResult(intent, Constant.INT_REQUEST_MAIN);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, Constant.INT_REQUEST_LOGIN);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.INT_REQUEST_LOGIN: {
                if (resultCode == RESULT_OK) {
                    // launch main actiivty
                    Intent intent = new Intent(this, MainActivity.class);
                    User user = (User)data.getSerializableExtra("User");
                    SerializeUtil.serialize(Constant.FILE_SAVED_USER, user);
                    startActivityForResult(intent, Constant.INT_REQUEST_MAIN);
                } else {
                   // RESULT_CANCEL OR OTHER CODE
                    finish();
                }
                break;
            }
            case Constant.INT_REQUEST_MAIN: {
                // access main activity result code, may have future use
                finish();
                break;
            }
        }

    }
}
