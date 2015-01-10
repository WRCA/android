package info.jiangchuan.wrca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.util.Log;

import android.app.LocalActivityManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.content.Intent;

import android.support.v7.app.ActionBar;

import android.app.Activity;

public class MainActivity extends Activity {

    private TabHost host;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createTabUI(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
    }

    private void createTabUI(Bundle savedInstanceState) {
        host = (TabHost)findViewById(android.R.id.tabhost);
        if (host == null) {
            Log.d(TAG, "mHost null");
        }

        LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
        localActivityManager.dispatchCreate(savedInstanceState);
        host.setup(localActivityManager);

        TabHost.TabSpec	tab1 = host.newTabSpec("tab1");
        TabHost.TabSpec	tab2 = host.newTabSpec("tab2");
        if (tab1 == null || tab2 == null) {
            Log.d(TAG, "tab null");
        }
        tab1.setIndicator("events");
        tab2.setIndicator("setting");
        // TODO: use intent
        tab1.setContent(new Intent(this, EventsActivity.class));
        tab2.setContent(new Intent(this, SettingsActivity.class));

        host.addTab(tab1);
        host.addTab(tab2);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
