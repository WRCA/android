package info.jiangchuan.wrca;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.preference.CheckBoxPreference;
import android.text.TextUtils;


import java.util.List;

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
}
