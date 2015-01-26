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
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder viewHolder;
        Event m = eventItems.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_event, null);

            viewHolder = new ViewHolder();

            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);
            viewHolder.address = (TextView) convertView.findViewById(R.id.address);

            if (imageLoader == null)
                imageLoader = WillowRidge.getInstance().getImageLoader();

            viewHolder.image = (NetworkImageView) convertView
                    .findViewById(R.id.thumbnail);
            viewHolder.image.setImageUrl(m.getThumbnailUrl(), imageLoader);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);

        // title
        viewHolder.title.setText(m.getTitle());

        // address
        viewHolder.address.setText("Location: " + String.valueOf(m.getLocation()));

        // time
        viewHolder.time.setText(m.getTime());

        return convertView;
    }
    static class ViewHolder {
        NetworkImageView image;
        TextView title;
        TextView address;
        TextView time;
    }
}
