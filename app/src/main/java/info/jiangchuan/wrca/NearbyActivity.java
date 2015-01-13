package info.jiangchuan.wrca;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.ListActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class NearbyActivity extends Activity {

    private static Activity activity;
    String[] category = new String[] { "Atm", "Bank", "Heath",
            "Convenience Store", "Food", "fire_station", "police", "hospital",
            "dentist", "doctor", "electrician", "pharmacy" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        activity = this;
        ListView listView = (ListView)findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(activity,
                        "Item in position " + category[position] + " clicked",
                        Toast.LENGTH_SHORT).show();
                // Return true to consume the click event. In this case the
                // onListItemClick listener is not called anymore.
            }
        });
    }


}
