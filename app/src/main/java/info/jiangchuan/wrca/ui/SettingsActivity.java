package info.jiangchuan.wrca.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.google.android.gcm.GCMRegistrar;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.MainActivity;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.account.LoginActivity;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;

public class SettingsActivity extends PreferenceActivity implements Preference.OnPreferenceClickListener{
    private static final boolean ALWAYS_SIMPLE_PREFS = false;
    private static SettingsActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        activity = this;
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_screen);
        setupListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        boolean hasNotifications = SharedPrefUtil.readBoolean(Constant.string_notifications);
//        if (!hasNotifications) {
//            GCMRegistrar.unregister(this);
//        }
    }



    void setupListener() {
       findPreference(getResources().getString(R.string.share_pref_log_out)).setOnPreferenceClickListener(this);
        findPreference(getResources().getString(R.string.share_pref_clear_history)).setOnPreferenceClickListener(this);
        findPreference(getResources().getString(R.string.share_pref_notification)).setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();
        if (key.compareTo(getResources().getString(R.string.share_pref_clear_history)) == 0) {
            WillowRidge.getInstance().getUser().getNotifications().clear();
            ToastUtil.showToast(WillowRidge.getInstance(), "clear");
            return true;

        } else if (key.compareTo(getResources().getString(R.string.share_pref_log_out)) == 0) {
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
            MainActivity.getActivity().finish();
            CheckBoxPreference box = (CheckBoxPreference) activity.findPreference("login");
            if (box != null) {
                box.setChecked(false);
            }
            return true;

        } else if (key.compareTo(getResources().getString(R.string.share_pref_notification)) == 0) {
            User user = WillowRidge.getInstance().getUser();
            CheckBoxPreference box = (CheckBoxPreference) preference;
            Boolean notification = box.isChecked();
            user.setNotification(notification);
            if (notification) {
                WillowRidge.getInstance().getGcmService().register(user);
            } else {
                WillowRidge.getInstance().getGcmService().unRegister();
            }
            return true;
        }
        return false;
    }
}

