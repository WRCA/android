package info.jiangchuan.wrca.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.WillowRidge;
import info.jiangchuan.wrca.util.SerializeUtil;

/**
 * Created by jiangchuan on 1/26/15.
 */
public class UserData {
    private static final String TAG = "UserData";
    private List<Event> events = new ArrayList<Event>();
    private List<Notification> notifications = new ArrayList<Notification>();
    private User user = new User();

    public User getUser() {
        return user;
    }


    public List<Notification> getNotifications() {
        return notifications;
    }

    public void loadUserData() {
        List<Notification> tmp = (ArrayList<Notification>) SerializeUtil.deSerialize(Constant.FILE_SAVED_NOTIFICATIONS);
        if (tmp != null) {
            Log.d(TAG, "notif:" + Integer.toString(notifications.size()));
            notifications.addAll(tmp);
        }

        List<Event> tmp2 = (ArrayList<Event>)SerializeUtil.deSerialize(Constant.FILE_SAVED_EVENTS);
        if (tmp2 != null) {
            Log.d(TAG, "events:" + Integer.toString(events.size()));
            events.addAll(tmp2);
        }

        this.user = (User)SerializeUtil.deSerialize(Constant.FILE_SAVED_USER);
    }

    public void storeUserData() {
        Log.d(TAG, "notif:" + Integer.toString(notifications.size()));
        SerializeUtil.serialize(Constant.FILE_SAVED_NOTIFICATIONS, notifications);

        Log.d(TAG, "events:" + Integer.toString(events.size()));
        SerializeUtil.serialize(Constant.FILE_SAVED_EVENTS, events);

        SerializeUtil.serialize(Constant.FILE_SAVED_USER, user);
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void storeUser() {
        SerializeUtil.serialize(Constant.FILE_SAVED_USER, user);
    }
}
