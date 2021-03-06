package info.jiangchuan.wrca.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import info.jiangchuan.wrca.R;


public class NearbyActivity extends Activity {

    private static final String TAG = "PlacesActivity";
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
                Intent intent = new Intent(activity, NearbyResultActivity.class);
                intent.putExtra("place", category[position]);
                startActivity(intent);
            }
        });

    }

}
