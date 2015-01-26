package info.jiangchuan.wrca;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import info.jiangchuan.wrca.adapters.NotificationsAdapter;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.util.Utility;


public class PushNotificationActivity extends ActionBarActivity {

    private static PushNotificationActivity activity;

    public static PushNotificationActivity getActivity() {
        return activity;
    }


    private ListView listView;
    private NotificationsAdapter adapter;

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_notification);
        getSupportActionBar().setTitle("Notifications");

        listView = (ListView) findViewById(R.id.list_view);
        adapter = new NotificationsAdapter(this, WillowRidge.getInstance().getNotifications());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_push_notification, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh: {
                //noinspection SimplifiableIfStatement
                adapter.notifyDataSetChanged();
                return true;
            }
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }

    public NotificationsAdapter getAdapter() {
        return adapter;
    }

    public ListView getListView() {

        return listView;
    }
}
