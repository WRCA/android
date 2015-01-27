package info.jiangchuan.wrca;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import info.jiangchuan.wrca.models.UserData;

public class MainActivity extends Activity {

    private TabHost host;

    private static final String TAG = "MainActivity";

    private UserData userData;
    private TabHost tabHost;


    public static MainActivity getActivity() {
        return activity;
    }

    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        userData = WillowRidge.getInstance().getUserData();
        userData.input();
        WillowRidge.getInstance().getGcmService().register(userData.getUser());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        tab4.setIndicator("Site");
        // TODO: use intent
        tab1.setContent(new Intent(this, EventsActivity.class));
        tab2.setContent(new Intent(this, PushNotificationActivity.class));
        tab3.setContent(new Intent(this, EDirActivity.class));
        tab4.setContent(new Intent(this, WebActivity.class));

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
    }

    @Override
    public void onStart() {
        super.onStart();
      //  Log.d(TAG, Integer.toString(WRCAApplication.getInstance().getSavedEvents().size()));
    }

    @Override
    protected void onPause() {
        userData.output();
        super.onPause();
    }

    public UserData getUserData() {
        return userData;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
