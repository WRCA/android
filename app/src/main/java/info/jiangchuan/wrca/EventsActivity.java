package info.jiangchuan.wrca;

/**
 * Created by jiangchuan on 1/3/15.
 */

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class EventsActivity extends ListActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] info = new String[] { "ea","eo","ee",
                "ei","ed","eh"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.row_events, R.id.label, info);
        setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
        //Intent intent = new Intent(this, EventsItemActivity.class);
        //startActivity(intent);
    }
}
