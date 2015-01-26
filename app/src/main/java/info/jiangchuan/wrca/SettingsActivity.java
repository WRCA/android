package info.jiangchuan.wrca;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import com.google.android.gcm.GCMRegistrar;

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
        Preference button = (Preference)findPreference("clear_push_history");
        button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference arg0) {
                //code for what you want it to do
                MainActivity.getActivity().getUserData().getNotifications().clear();
                ToastUtil.showToastMessage(WillowRidge.getInstance(), "clear");
                PushNotificationActivity.getActivity().getAdapter().notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean hasNotifications = SharedPrefUtil.readBooleanSharedPreferences(Constant.string_notifications);
        if (!hasNotifications) {
            GCMRegistrar.unregister(this);
        }
    }
}

