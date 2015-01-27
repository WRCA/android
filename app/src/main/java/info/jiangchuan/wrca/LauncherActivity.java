package info.jiangchuan.wrca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import info.jiangchuan.wrca.account.LoginActivity;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.PersisUtil;
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
        /*
         * check system env
         * check user env
         */
        if (isSystemReady()) {
            if (isUserReady()) {
                // auto login
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // require login
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        } else {

        }
    }

    private boolean isSystemReady() {
        // check network connection
        if (!NetworkUtil.hasInternet(this)) {
            return false;
        }
        // check gcm service
        if (!WillowRidge.getInstance().getGcmService().isGCMConfigSet()) {
            return false;
        }
        return true;
    }

    private boolean isUserReady() {
        User user = WillowRidge.getInstance().getUser();
        PersisUtil.read(user);
        return user.isAutoLogin() == true &&
                TextUtils.isEmpty(user.getToken()) == false;
    }
}
