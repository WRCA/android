package info.jiangchuan.wrca;

/**
 * Created by jiangchuan on 1/3/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.adapters.EventAdapter;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.parsers.EventParser;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

public class EventsActivity extends ActionBarActivity {

    private static final String TAG = "EventsActivity";
    private List<Event> events = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    private EventsActivity mActivity;

    private int lastItem = 0;
    private boolean isloading = false;
    private String range = "all";

    final int limit = 6;
    int offset = 1;

    private ListView.OnScrollListener listener = new Listener();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        mActivity = this;
        setupListview();
        getSupportActionBar().setTitle("All Events");
    }

    void setupListview() {
        listView = (ListView) findViewById(R.id.list);

        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);

        String url = "http://jiangchuan.info/php/index.php?object=events&type=all&offset=" + Integer.toString(lastItem+1);
        listView.setOnScrollListener(listener);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mActivity, EventDetailActivity.class);
                intent.putExtra("event", events.get(position));
                startActivity(intent);
            }
        });

        range = "all";
        refresh();
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

    public ListView getListView() {
        return listView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.item_saved: {
                Intent intent = new Intent(this, SavedEventsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.item_this_week:
                onEventsThisWeek();
                getSupportActionBar().setTitle("This Week");
                break;
            case R.id.item_this_month:

                onEventsThisMonth();
                getSupportActionBar().setTitle("This Month");
                break;
            case R.id.item_all:
                onEventsAll();
                getSupportActionBar().setTitle("All Events");
                break;
            case R.id.action_settings: {
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onEventsThisWeek() {
        events.clear();
        offset = 1; // reset
        listView.setOnScrollListener(listener);
        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);
        range = "week";
        refresh();
    }
    private void onEventsThisMonth() {
        events.clear();
        offset = 1; // reset
        listView.setOnScrollListener(listener);
        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);
        range = "month";
        refresh();
    }
    private void onEventsAll() {
        events.clear();
        offset = 1; // reset
        listView.setOnScrollListener(listener);
        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);
        range = "all";
        refresh();
    }
    private void onLoadMore() {
        // Creating volley request obj
       // String url = "http://jiangchuan.info/php/index.php?object=events&type=all&offset=" + Integer.toString(lastItem + 1);
       // getEventsFromURL(url);
        User user = WillowRidge.getInstance().getUser();
        if (user.getToken() == null || range == null) {
            Log.e(TAG, "param cannot be null");
            return;
        }
        Client.getApi().events(user.getToken(), range, Integer.toString(offset), Integer.toString(limit), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                Result result = ResultParser.parse(jsonObject);
                switch (result.getStatus()) {
                    case 200: {
                        try {
                            //JSONArray array = jsonObject.get(EventParser.EVENTS).getAsJsonArray();
                            JsonArray jsonArray = jsonObject.get(EventParser.EVENTS).getAsJsonArray();
                            events.addAll(EventParser.parse(jsonArray));
                            int list_count = jsonObject.get(EventParser.LIST_COUNT).getAsInt();
                            Log.d(TAG, Integer.toString(list_count));
                            if (offset + limit > list_count) {
                                listView.setOnScrollListener(null); // no more data, close
                            } else {
                                offset = offset + limit;
                            }
                            adapter.notifyDataSetChanged();
                            isloading = false;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    default: {
                        listView.setOnScrollListener(null);
                        ToastUtil.showToastMessage(mActivity, jsonObject.toString());
                        break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                listView.setOnScrollListener(null);
                Log.e(TAG, error.toString());
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    private void refresh() {
      onLoadMore();
    }

    class Listener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            // threshold being indicator if bottom of list is hit
            if (isloading == false && firstVisibleItem == totalItemCount - 5) {
                isloading = true;
                onLoadMore();
            }
        }
    }
}
