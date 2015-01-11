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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private TabHost host;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Event> list = WRCAApplication.getInstance().getSavedEvents();
        list = Utility.readSavedEventsFromFile();
        Log.d(TAG, Integer.toString(list.size()));
        Log.d(TAG, "onCreate");
        createTabUI(savedInstanceState);
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
        TabHost.TabSpec	tab3 = host.newTabSpec("tab3");
        if (tab1 == null || tab2 == null || tab3 == null) {
            Log.d(TAG, "tab null");
        }
        tab1.setIndicator("events");
        tab2.setIndicator("E-Dir");
        tab3.setIndicator("setting");
        // TODO: use intent
        tab1.setContent(new Intent(this, EventsActivity.class));
        tab2.setContent(new Intent(this, EDirActivity.class));
        tab3.setContent(new Intent(this, SettingsActivity.class));

        host.addTab(tab1);
        host.addTab(tab2);
        host.addTab(tab3);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, Integer.toString(WRCAApplication.getInstance().getSavedEvents().size()));
        Log.d(TAG, "onStop");
        Utility.writeSavedEventsToFile( (ArrayList<Event>)WRCAApplication.getInstance().getSavedEvents());
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
      //  Log.d(TAG, Integer.toString(WRCAApplication.getInstance().getSavedEvents().size()));
    }
}
