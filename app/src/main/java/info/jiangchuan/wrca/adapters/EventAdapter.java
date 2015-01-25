package info.jiangchuan.wrca.adapters;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.models.Event;

/**
 * Created by jiangchuan on 1/4/15.
 */
public class EventAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Event> eventItems;
    ImageLoader imageLoader = WillowRidge.getInstance().getImageLoader();

    public EventAdapter(Activity activity, List<Event> eventItems) {
        this.activity = activity;
        this.eventItems = eventItems;
    }

    @Override
    public int getCount() {
        return eventItems.size();
    }

    @Override
    public Object getItem(int location) {
        return eventItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row_event, null);

        if (imageLoader == null)
            imageLoader = WillowRidge.getInstance().getImageLoader();

        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.address);
        TextView time = (TextView) convertView.findViewById(R.id.time);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        Event m = eventItems.get(position);

        // thumbnail image
        thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        title.setText(m.getTitle());

        // rating
        rating.setText("Location: " + String.valueOf(m.getLocation()));

        // time
        time.setText(m.getTime());

        return convertView;
    }
}
