package info.jiangchuan.wrca;

/**
 * Created by jiangchuan on 1/3/15.
 */

import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;
import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.support.v7.app.ActionBarActivity;

import android.widget.AbsListView;

import info.jiangchuan.wrca.adapters.EventAdapter;
import info.jiangchuan.wrca.models.Event;

public class EventsActivity extends ActionBarActivity {

    private static final String TAG = "EventsActivity";
    private List<Event> eventsList = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    private EventsActivity mActivity;

    private int lastItem = 0;

    private boolean loading = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        mActivity = this;
        listView = (ListView) findViewById(R.id.list);

        adapter = new EventAdapter(this, eventsList);
        listView.setAdapter(adapter);

        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&offset=" + Integer.toString(lastItem+1);
        getEventsFromURL(url);
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

                if (loading == false && firstVisibleItem == totalItemCount-5) {
                    loading = true;
                    pullMoreData();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mActivity, EventDetailActivity.class);
                intent.putExtra("event", eventsList.get(position));
                startActivity(intent);
            }
        });

        getSupportActionBar().setTitle("All Events");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_events, menu);
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

        switch (id) {
            case R.id.item_saved:
                Intent intent = new Intent(this, SavedEventsActivity.class);
                startActivity(intent);
                break;
            case R.id.item_this_week:
                listView.setOnScrollListener(null);
                onEventsThisWeek();
                getSupportActionBar().setTitle("This Week");
                break;
            case R.id.item_this_month:
                listView.setOnScrollListener(null);
                onEventsThisMonth();
                getSupportActionBar().setTitle("This Month");
                break;
            case R.id.item_all:
                onEventsAll();
                getSupportActionBar().setTitle("All Events");
                break;
            case android.R.id.home:
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onEventsThisWeek() {
        eventsList.clear();
        adapter = new EventAdapter(this, eventsList);
        listView.setAdapter(adapter);
        String url = "http://jiangchuan.info/php/index.php?object=events&type=week";
        getEventsFromURL(url);
    }
    private void onEventsThisMonth() {
        eventsList.clear();
        adapter = new EventAdapter(this, eventsList);
        listView.setAdapter(adapter);
        String url = "http://jiangchuan.info/php/index.php?object=events&type=month";
        getEventsFromURL(url);
    }
    private void onEventsAll() {
        eventsList.clear();
        adapter = new EventAdapter(this, eventsList);
        listView.setAdapter(adapter);
        lastItem = 0;
        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&offset=" + Integer.toString(lastItem+1);
        getEventsFromURL(url);
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

                if (loading == false && firstVisibleItem == totalItemCount-5) {
                    loading = true;
                    pullMoreData();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mActivity, EventDetailActivity.class);
                intent.putExtra("event", eventsList.get(position));
                startActivity(intent);
            }
        });
    }
    private void pullMoreData() {
        // Creating volley request obj
        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&offset=" + Integer.toString(lastItem + 1);
        Log.d(TAG, "pull more data");
        getEventsFromURL(url);
        // Adding request to request queue
    }

   void getEventsFromURL(String url) {
       JsonObjectRequest eventReq = new JsonObjectRequest(Request.Method.GET, url, null,
               new Response.Listener<JSONObject>() {
                   @Override
                   public void onResponse(JSONObject response) {
                       try {
                           // Parsing json
                           int code = response.getInt("success");
                           if (code == 0) {
                               listView.setOnScrollListener(null);
                               Log.d(TAG, "no data left");
                               return;
                           }
                           String json = response.getString("events");
                           JSONArray arr = new JSONArray(json);
                           lastItem += arr.length();
                           for (int i = 0; i < arr.length(); i++) {
                               JSONObject obj = arr.getJSONObject(i);
                               Event movie = new Event();
                               movie.setTitle(obj.getString("title"));
                               movie.setThumbnailUrl(obj.getString("thumbnail_url"));
                               movie.setLocation(obj.getString("location"));
                               movie.setDescription(obj.getString("description"));
                               movie.setTime(obj.getString("time"));

                               // adding movie to movies array
                               eventsList.add(movie);

                           }

                       } catch (JSONException e) {
                           e.printStackTrace();
                       }
                       adapter.notifyDataSetChanged();
                       loading = false;
                   }
               }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               VolleyLog.d(TAG, "Error: " + error.getMessage());
           }
       });
       // Adding request to request queue
       WRCAApplication.getInstance().getRequestQueue().add(eventReq);
   }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }
}
