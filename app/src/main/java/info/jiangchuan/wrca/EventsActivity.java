package info.jiangchuan.wrca;

/**
 * Created by jiangchuan on 1/3/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.ArrayList;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import android.widget.AbsListView;
public class EventsActivity extends Activity {

    private static final String TAG = "EventsActivity";
    private List<Event> eventsList = new ArrayList<Event>();
    private ListView listView;
    private CustomListAdapter adapter;

    private int lastItem = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        listView = (ListView) findViewById(R.id.list);


        adapter = new CustomListAdapter(this, eventsList);
        listView.setAdapter(adapter);

        //pullMoreData();
        // Creating volley request obj
        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&cursorPos=" + lastItem+1;
        JsonArrayRequest eventReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lastItem += response.length();
                        // Parsing json
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event movie = new Event();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("thumbnail_url"));
                                movie.setLocation(obj.getString("location"));
                                movie.setTime(obj.getString("time"));

                                // adding movie to movies array
                                eventsList.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });
        // Adding request to request queue
        WRCAApplication.getInstance().getRequestQueue().add(eventReq);
        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view,
                                             int scrollState) {
                // Do nothing
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {

                // threshold being indicator if bottom of list is hit

                if (firstVisibleItem == totalItemCount-5) {
                    pullMoreData();
                }
            }
        });
    }

    private void pullMoreData() {
        // Creating volley request obj
        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&cursorPos=" + 3;
        Log.d(TAG, "pull more data");
        JsonArrayRequest eventReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        lastItem += response.length();
                        // Parsing json
                        //Log.d(TAG, Integer.toString(eventsList.size()));
                        for (int i = 0; i < response.length(); i++) {
                            try {

                                JSONObject obj = response.getJSONObject(i);
                                Event movie = new Event();
                                movie.setTitle(obj.getString("title"));
                                movie.setThumbnailUrl(obj.getString("thumbnail_url"));
                                movie.setLocation(obj.getString("location"));
                                movie.setTime(obj.getString("time"));

                                // adding movie to movies array
                                eventsList.add(movie);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
                        Log.d(TAG, Integer.toString(eventsList.size()));
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d(TAG, "Error: " + error.toString());
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null)
                Log.e(TAG, Integer.toString(networkResponse.statusCode));
            }
        });
        // Adding request to request queue
        WRCAApplication.getInstance().getRequestQueue().add(eventReq);
    }

}
