package info.jiangchuan.wrca.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.adapters.PlaceAdapter;
import info.jiangchuan.wrca.models.Place;

public class NearbyResultActivity extends ActionBarActivity {

    private static final String TAG = "NearbyResultActivity";
    private ArrayList<Place> placeList = new ArrayList<Place>();
    private ListView listView;
    private PlaceAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_result);
        getSupportActionBar().setTitle("Nearby Result");
        setupListView();
    }

    public void setupListView() {
        listView = (ListView) findViewById(R.id.list);
        adapter = new PlaceAdapter(this, placeList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q=" + placeList.get(position).getLocation()));
                startActivity(intent);
            }
        });
        String type = getIntent().getStringExtra("place");
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+type+
                "&location=43.012062,-78.80952977&radius=3000"+"&key=AIzaSyBzNeQOQWOXUwgyC6v5JPVlRhralZ2FKWM";


        if (Constant.DEBUG)
            Log.d(TAG, url);
        JsonObjectRequest eventReq = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parsing json
                            JSONArray arr  = response.getJSONArray("results");
                            if (Constant.DEBUG)
                                Log.d(TAG, Integer.toString(arr.length()));
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);
                                Place place = new Place();
                                place.setTitle(obj.getString("name"));
                                place.setThumbnailUrl(obj.getString("icon"));
                                place.setLocation(obj.getString("formatted_address"));
                                //place.setTime(obj.getString("opening_hours"));

                                // adding place to movies array
                                placeList.add(place);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (Constant.DEBUG)
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        WillowRidge.getInstance().getRequestQueue().add(eventReq);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby_result, menu);
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
}
