package info.jiangchuan.wrca;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.google.android.gcm.GCMRegistrar;

import info.jiangchuan.wrca.account.LoginActivity;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;

public class SettingsActivity extends PreferenceActivity {
    private static final boolean ALWAYS_SIMPLE_PREFS = false;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_screen);
        //    ActionBar actionBar = getSupportActionBar();
        // actionBar.setDisplayHomeAsUpEnabled(true);
        setupListener();
    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean hasNotifications = SharedPrefUtil.readBoolean(Constant.string_notifications);
        if (!hasNotifications) {
            GCMRegistrar.unregister(this);
        }
    }

    void setupListener() {
        Preference button = (Preference)findPreference(
                getResources().getString(R.string.share_pref_clear_history));

        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                //code for what you want it to do
                WillowRidge.getInstance().getUser().getNotifications().clear();
                ToastUtil.showToast(WillowRidge.getInstance(), "clear");
                PushNotificationActivity.getActivity().getAdapter().notifyDataSetChanged();
                return true;
            }
        });

        button = (Preference)findPreference(
                getResources().getString(R.string.share_pref_log_out));

        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                //code for what you want it to do
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
                finish();
                MainActivity.getActivity().finish();
                return true;
            }
        });

    }
}

