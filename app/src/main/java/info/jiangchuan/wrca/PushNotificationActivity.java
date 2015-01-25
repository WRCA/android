package info.jiangchuan.wrca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.adapters.EventAdapter;
import info.jiangchuan.wrca.adapters.NotificationsAdapter;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.util.Utility;


public class PushNotificationActivity extends ActionBarActivity {

    private List<Notification> list = new ArrayList<Notification>();
    private static PushNotificationActivity activity;

    public static PushNotificationActivity getActivity() {
        return activity;
    }

    public List<Notification> getList() {
        return list;
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
        adapter = new NotificationsAdapter(this, list);
        listView.setAdapter(adapter);
        List<Notification> tmp = Utility.readNotificationList();
        if (tmp != null) {
            list.addAll(tmp);
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_refresh) {
            adapter.notifyDataSetChanged();
            return true;
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
