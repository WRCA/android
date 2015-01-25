package info.jiangchuan.wrca;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.Window;

import com.google.android.gcm.GCMRegistrar;

import info.jiangchuan.wrca.util.ToastUtil;
import info.jiangchuan.wrca.util.Utility;

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
                WillowRidge.getInstance().getNotifications().clear();
                ToastUtil.showToastMessage(WillowRidge.getInstance(), "clear");
                PushNotificationActivity.getActivity().getAdapter().notifyDataSetChanged();
                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        boolean hasNotifications = Utility.readBooleanSharedPreferences(Constants.string_notifications);
        if (!hasNotifications) {
            GCMRegistrar.unregister(this);
        }
    }
}

