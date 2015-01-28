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
import info.jiangchuan.wrca.models.Notification;

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
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder viewHolder;
        int size = notifications.size();
        Notification notification = notifications.get(size - position - 1);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_row_notification, null);
            viewHolder = new ViewHolder();



            viewHolder.content = (TextView) convertView.findViewById(R.id.content);
            viewHolder.time = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(viewHolder);

        } else {
           viewHolder = (ViewHolder) convertView.getTag();
        }

        // content
        viewHolder.content.setText(notification.getContent());

        // time
        viewHolder.time.setText(String.valueOf(notification.getTime()));

        return convertView;
    }
    static class ViewHolder {
        TextView content;
        TextView time;
    }
}
