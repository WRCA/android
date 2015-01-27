package info.jiangchuan.wrca;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.List;

import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.util.SharedPrefUtil;
import info.jiangchuan.wrca.util.ToastUtil;

public class EventDetailActivity extends ActionBarActivity {

    ImageView mImageView;
    private static final String TAG = "EventDetailActivity";
    private EventDetailActivity mActivity;
    private Event event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        mActivity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final Event event = (Event)getIntent().getSerializableExtra("event");
        this.setTitle("Event Detail");
        this.event = event;

        // set image
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear_layout_image);
        ImageContainer imageContainer = WillowRidge.getInstance().getImageLoader().get(event.getThumbnailUrl(),new ImageListener() {
            @Override
            public void onResponse(ImageContainer response, boolean isImmediate) {
                mActivity.findViewById(R.id.linear_layout_image).setBackground(new BitmapDrawable(response.getBitmap()));
                TextView title = (TextView)findViewById(R.id.text_view_title);
                title.setText(event.getTitle());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, linearLayout.getWidth(), linearLayout.getHeight());


        TextView location = (TextView)findViewById(R.id.text_view_address);
        location.setText(event.getLocation());

        TextView time = (TextView)findViewById(R.id.text_view_time);
        time.setText(event.getTime());

        TextView description = (TextView)findViewById(R.id.text_view_description);
        description.setText(event.getDescription());

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start map activity
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start detail activity
                Intent intent = new Intent(mActivity, EventDescriptionActivity.class);
                intent.putExtra("event", event);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_save: {
                List<Event> list = WillowRidge.getInstance().getUser().getEvents();
                Event event = (Event)getIntent().getSerializableExtra("event");
                if (SharedPrefUtil.containsEvent(list, event)) {
                    ToastUtil.showToast(this, "event already saved");
                } else {
                    list.add(event);
                    ToastUtil.showToast(this, "event saved");
                }
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void launchMap(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q="+this.event.getLocation()));
        startActivity(intent);

    }

    public void shareWithFriend(View view) {
        String message = "";
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(i, "Share with friends"));
    }
}
