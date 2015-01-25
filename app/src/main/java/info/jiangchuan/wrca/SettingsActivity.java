package info.jiangchuan.wrca;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.google.android.gcm.GCMRegistrar;


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

