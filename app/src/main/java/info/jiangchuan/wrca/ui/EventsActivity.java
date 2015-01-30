package info.jiangchuan.wrca.ui;

/**
 * Created by jiangchuan on 1/3/15.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SpinnerAdapter;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.adapters.EventAdapter;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Result;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.parsers.EventParser;
import info.jiangchuan.wrca.parsers.ResultParser;
import info.jiangchuan.wrca.rest.Client;
import info.jiangchuan.wrca.util.DialogUtil;
import info.jiangchuan.wrca.util.ToastUtil;
import retrofit.Callback;
import retrofit.RetrofitError;

public class EventsActivity extends ActionBarActivity
        implements AbsListView.OnScrollListener,
        AdapterView.OnItemClickListener,
        ActionBar.OnNavigationListener {

    private static final String TAG = "EventsActivity";
    private List<Event> events = new ArrayList<Event>();
    private ListView listView;
    private EventAdapter adapter;
    private EventsActivity mActivity;

    private boolean isloading = false;
    private String range = "all";
    private boolean searchViewOpen = false;

    final int limit = 5;
    int offset = 1;

    View footer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mActivity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        DialogUtil.setup(this);
        DialogUtil.showProgressDialog("Loading...");
        setupListview();
        setupActionBar();
    }

    void setupActionBar() {
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.actionbar_events_list, android.R.layout.simple_dropdown_item_1line);

        ActionBar bar = getSupportActionBar();
        bar.setListNavigationCallbacks(mSpinnerAdapter, this);
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        bar.setHomeButtonEnabled(true);

    }
    void setupListview() {
        listView = (ListView) findViewById(R.id.list);
        footer = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_footer, null, false);
        listView.addFooterView(footer, null, false);
        adapter = new EventAdapter(this, events);
        listView.setAdapter(adapter);
        footer.setVisibility(View.GONE);
       // listView.setOnScrollListener(this);
        listView.setOnItemClickListener(this);
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

        switch (id) {
            case R.id.action_search: {
                try {
                    if (!searchViewOpen) {
                        LinearLayout hiddenLayout = (LinearLayout)findViewById(R.id.layout_hidden);
                        if (hiddenLayout == null) {
                            LinearLayout myLayout = (LinearLayout)findViewById(R.id.linear_layout_above);
                            View hiddenInfo = getLayoutInflater().inflate(R.layout.layout_search_view, myLayout, false);
                            myLayout.addView(hiddenInfo);
                        }
                        searchViewOpen = true;
                    }
                    else {
                        View myView = findViewById(R.id.layout_hidden);
                        ViewGroup parent = (ViewGroup) myView.getParent();
                        parent.removeView(myView);
                        searchViewOpen = false;
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }
            case R.id.item_saved: {
                Intent intent = new Intent(this, SavedEventsActivity.class);
                startActivity(intent);
                return true;
            }
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
        range = "week";
        refresh();
    }
    private void onEventsThisMonth() {
        range = "month";
        refresh();
    }
    private void onEventsAll() {
        range = "all";
        refresh();
    }
    private void onLoadMore() {
        User user = WillowRidge.getInstance().getUser();
        if (user.getToken() == null || range == null) {
            Log.e(TAG, "param cannot be null");
            return;
        }
        if (footer != null && isloading == false) {
            footer.setVisibility(View.VISIBLE);
        }

        Client.getApi().events(user.getToken(), range, Integer.toString(offset), Integer.toString(limit), new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, retrofit.client.Response response) {
                DialogUtil.hideProgressDialog();
                if (footer != null) {
                    footer.setVisibility(View.GONE);
                }
                Result result = ResultParser.parse(jsonObject);
                switch (result.getStatus()) {
                    case 200: {
                        try {
                            //JSONArray array = jsonObject.get(EventParser.EVENTS).getAsJsonArray();
                            JsonArray jsonArray = jsonObject.get(EventParser.EVENTS).getAsJsonArray();
                            events.addAll(EventParser.parse(jsonArray));
                            int list_count = jsonObject.get(EventParser.LIST_COUNT).getAsInt();
                            if (offset + limit > list_count) {
                                listView.setOnScrollListener(null); // no more data, close
                                isloading = false;
                                listView.removeFooterView(footer);
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
                        // other err
                        listView.setOnScrollListener(null);
                        isloading = false;
                        ToastUtil.showToast(mActivity, result.getMessage());
                        break;
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (footer != null) {
                    footer.setVisibility(View.GONE);
                }
                listView.setOnScrollListener(null);
                DialogUtil.hideProgressDialog();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void refresh() {
        events.clear();
        adapter.notifyDataSetChanged();
        isloading = false;
        offset = 1;
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int num = visibleItemCount + firstVisibleItem;
        if (isloading == false && num >= totalItemCount
                && footer != null && footer.getVisibility() == View.GONE) {
            isloading = true;
            footer.setVisibility(View.VISIBLE);
            //Log.d(TAG, "onScroll");
            onLoadMore();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mActivity, EventDetailActivity.class);
        intent.putExtra("event", events.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        switch (i) {
            case 0:
                onEventsAll();
                return true;
            case 1:
                onEventsThisWeek();
                return true;
            case 2:
                onEventsThisMonth();
                return true;
        }
        return false;
    }
}

