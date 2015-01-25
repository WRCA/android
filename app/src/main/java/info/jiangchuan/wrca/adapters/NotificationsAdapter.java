package info.jiangchuan.wrca.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import info.jiangchuan.wrca.R;
import info.jiangchuan.wrca.WRCAApplication;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.models.Place;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class NotificationsAdapter extends BaseAdapter{

    private Activity activity;
    private LayoutInflater inflater;
    private List<Notification> notifications;

    public NotificationsAdapter(Activity activity, List<Notification> notifications) {
        this.activity = activity;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int position) {
        return notifications.get(position);
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
            convertView = inflater.inflate(R.layout.list_row_notification, null);


        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView time = (TextView) convertView.findViewById(R.id.time);

        // getting movie data for the row
        Notification m = notifications.get(position);

        content.setText(m.getContent());

        // rating
        time.setText(String.valueOf(m.getTime()));

        return convertView;
    }
}
