package info.jiangchuan.wrca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.drawable.*;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import java.util.BitSet;

public class EventDetailActivity extends ActionBarActivity {

    ImageView mImageView;
    private static final String TAG = "EventDetailActivity";
    private EventDetailActivity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        mActivity = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        final Event event = (Event)getIntent().getSerializableExtra("event");
        this.setTitle(event.getTitle());


        // set image
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.linear_layout_image);
        ImageContainer imageContainer = WRCAApplication.getInstance().getImageLoader().get(event.getThumbnailUrl(),new ImageListener() {
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


        TextView location = (TextView)findViewById(R.id.text_view_location);
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

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.action_save) {
            Event event = (Event)getIntent().getSerializableExtra("event");
            WRCAApplication.getInstance().getSavedEvents().add(event);
            Toast toast = Toast.makeText(this, "event save", Toast.LENGTH_SHORT);
            toast.show();

        }

        return super.onOptionsItemSelected(item);
    }

    private class LayoutParams {
    }
}
