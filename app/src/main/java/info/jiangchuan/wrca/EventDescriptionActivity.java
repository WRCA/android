package info.jiangchuan.wrca;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.util.Log;
import android.widget.TextView;

import info.jiangchuan.wrca.models.Event;

public class EventDescriptionActivity extends ActionBarActivity {

    TextView viewTitle;
    TextView viewContent;
    private static final String TAG = "EventDescriptionActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        findView();
        final Event event = (Event)getIntent().getSerializableExtra("event");

        viewTitle.setText(event.getTitle());
        viewContent.setText(event.getDescription());
        Log.d("show", event.getDescription());
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

    private void findView() {
       viewTitle = (TextView) findViewById(R.id.title);
        viewContent = (TextView) findViewById(R.id.content);
    }
}
