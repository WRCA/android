package info.jiangchuan.wrca;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.util.Utility;
public class MainActivity extends Activity {

    private TabHost host;

    private static final String TAG = "MainActivity";

    public static String name;
    public static String email;
    private TabHost tabHost;

    public static MainActivity getActivity() {
        return activity;
    }

    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Event> list = WillowRidge.getInstance().getSavedEvents();
        list = Utility.readSavedEventsFromFile();
        Log.d(TAG, Integer.toString(list.size()));
        Log.d(TAG, "onCreate");
        setupTabs(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.saveNotificationList(WillowRidge.getInstance().getNotifications());
    }

    public TabHost getHost() {
        return host;
    }

    private void setupTabs(Bundle savedInstanceState) {
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
        TabHost.TabSpec	tab4 = host.newTabSpec("tab4");
        if (tab1 == null || tab2 == null || tab3 == null || tab4 == null) {
            Log.d(TAG, "tab null");
        }
        tab1.setIndicator("events");
        tab2.setIndicator("push");
        tab3.setIndicator("E-Dir");
        tab4.setIndicator("setting");
        // TODO: use intent
        tab1.setContent(new Intent(this, EventsActivity.class));
        tab2.setContent(new Intent(this, PushNotificationActivity.class));
        tab3.setContent(new Intent(this, EDirActivity.class));
        tab4.setContent(new Intent(this, SettingsActivity.class));

        host.addTab(tab1);
        host.addTab(tab2);
        host.addTab(tab3);
        host.addTab(tab4);

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int id = host.getCurrentTab();
                switch (id) {
                    case 0:
                        Log.d(TAG, "0");
                        break;
                    case 1:
                        Log.d(TAG, "1");
                        PushNotificationActivity.getActivity().getAdapter().notifyDataSetChanged();
                        break;
                }
            }
        });
        tabHost = host;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, Integer.toString(WillowRidge.getInstance().getSavedEvents().size()));
        Log.d(TAG, "onStop");
        Utility.writeSavedEventsToFile( (ArrayList<Event>) WillowRidge.getInstance().getSavedEvents());
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
      //  Log.d(TAG, Integer.toString(WRCAApplication.getInstance().getSavedEvents().size()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
