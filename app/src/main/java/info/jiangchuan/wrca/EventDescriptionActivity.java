package info.jiangchuan.wrca;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ActionBar;

import android.util.Log;

public class EventDescriptionActivity extends ActionBarActivity {

    private static final String TAG = "EventDescriptionActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
