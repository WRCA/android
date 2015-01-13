package info.jiangchuan.wrca;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TabHost;

public class EDirActivity extends ActionBarActivity {

    private static final String TAG = "EDirActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edir);
        createTabUI(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edir, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void createTabUI(Bundle savedInstanceState) {
        TabHost host;
        host = (TabHost)findViewById(android.R.id.tabhost);
        if (host == null) {
            Log.d(TAG, "mHost null");
        }

        LocalActivityManager localActivityManager = new LocalActivityManager(this, false);
        localActivityManager.dispatchCreate(savedInstanceState);
        host.setup(localActivityManager);

        TabHost.TabSpec	tab1 = host.newTabSpec("tab1");
        TabHost.TabSpec	tab2 = host.newTabSpec("tab2");
        if (tab1 == null || tab2 == null ) {
            Log.d(TAG, "tab null");
        }
        tab1.setIndicator("Nearby");
        tab2.setIndicator("Members");
        // TODO: use intent
        tab1.setContent(new Intent(this, NearbyActivity.class));
        tab2.setContent(new Intent(this, MemberActivity.class));

        host.addTab(tab1);
        host.addTab(tab2);
    }
}
