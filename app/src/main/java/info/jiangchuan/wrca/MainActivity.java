package info.jiangchuan.wrca;

import android.app.Activity;
import android.app.Dialog;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TabHost;

import info.jiangchuan.wrca.dialogs.OpenWifiSettingDialog;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.ui.EDirActivity;
import info.jiangchuan.wrca.ui.EventsActivity;
import info.jiangchuan.wrca.ui.PushNotificationActivity;
import info.jiangchuan.wrca.ui.WebActivity;
import info.jiangchuan.wrca.util.DialogUtil;
import info.jiangchuan.wrca.util.NetworkUtil;
import info.jiangchuan.wrca.util.PersisUtil;

public class MainActivity extends Activity
        implements TabHost.OnTabChangeListener{

    private static final String TAG = "MainActivity";

    final int TAB_EVENTS = 0;
    final int TAB_NOTIFICATIONS = 1;
    final int TAB_EDIR = 2;
    final int TAB_SITE = 3;

    public static MainActivity activity;
    private TabHost tabHost;

    public static MainActivity getActivity() {
        return activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        DialogUtil.setup(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupTabs(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public TabHost getTabHost() {
        return tabHost;
    }

    private void setupTabs(Bundle savedInstanceState) {
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        if (tabHost == null) {
            Log.d(TAG, "mHost null");
        }

        LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
        localActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(localActivityManager);

        TabHost.TabSpec	tab1 = tabHost.newTabSpec("tab1");
        TabHost.TabSpec	tab2 = tabHost.newTabSpec("tab2");
        TabHost.TabSpec	tab3 = tabHost.newTabSpec("tab3");
        TabHost.TabSpec	tab4 = tabHost.newTabSpec("tab4");
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

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);

        tabHost.setOnTabChangedListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (NetworkUtil.hasInternet(this) == false) {
            Dialog dialog = new OpenWifiSettingDialog(this);
            dialog.show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        User user = WillowRidge.getInstance().getUser();
        PersisUtil.write(user);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onTabChanged(String tabId) {
        int id = tabHost.getCurrentTab();
        switch (id) {
            case TAB_EVENTS:
                break;
            case TAB_NOTIFICATIONS:
                PushNotificationActivity.getActivity().getAdapter().notifyDataSetChanged();
                break;
            case TAB_EDIR:
                break;
            case TAB_SITE:
                break;
        }
    }
}
