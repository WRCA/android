package info.jiangchuan.wrca.ui;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.adapters.EventAdapter;

public class SavedEventsActivity extends ActionBarActivity {

    private static final String TAG = "TAG";
    static SavedEventsActivity activity;
    private static final int DIALOG_ALERT = 10;
    ListView listView;
    BaseAdapter adapter;
    int item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_events);
        setupListView();
        getSupportActionBar().setTitle("Saved Events");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        WillowRidge.getInstance().getUser().getEvents().remove(this.item);
        if (adapter == null) {
            return true;
        }

        adapter.notifyDataSetInvalidated();
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(WillowRidge.getInstance().getUser()
                .getEvents().get(info.position).getTitle());
        menu.add("delete");
        this.item = info.position;
    }

    void setupListView() {
        adapter = new EventAdapter(this, WillowRidge.getInstance().getUser().getEvents());
        listView = (ListView)findViewById(R.id.list_view_saved_events);
        registerForContextMenu(listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(activity, EventDetailActivity.class);
                intent.putExtra("event",
                        WillowRidge.getInstance().getUser().getEvents().get(position));
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case android.R.id.home: {
                finish();
                return true;
            }
            case R.id.action_clear: {
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Delete all Items")
                        .setMessage("Are you sure you want to Clear all items?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                WillowRidge.getInstance().getUser().getEvents().clear();
                                adapter.notifyDataSetChanged();
                            }

                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
