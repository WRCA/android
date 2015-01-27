package info.jiangchuan.wrca.models;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.util.SerializeUtil;
import info.jiangchuan.wrca.util.SharedPrefUtil;

/**
 * Created by jiangchuan on 1/26/15.
 */
public class UserData {
    private static final String TAG = "UserData";

    // user saved events
    private List<Event> events = new ArrayList<Event>();

    // saved notifications
    private List<Notification> notifications = new ArrayList<Notification>();

    // user account & preference
    private User user = new User();

    public User getUser() {
        return user;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void input() {
        notifications.clear();
        events.clear();
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

        this.user = loadUser();
    }

    public void output() {
        SerializeUtil.serialize(Constant.FILE_SAVED_NOTIFICATIONS, notifications);
        SerializeUtil.serialize(Constant.FILE_SAVED_EVENTS, events);
        storeUser();
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void storeUser() {
        SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, user.getToken());
        SharedPrefUtil.writeBoolean(Constant.STRING_SILENCE, user.isSilent());
        SharedPrefUtil.writeBoolean(Constant.STRING_NOTIFICATION, user.isNotification());
        SharedPrefUtil.writeBoolean(Constant.STRING_AUTO_LOGIN, user.isAutoLogin());
    }
    public User loadUser() {
        user.setToken(SharedPrefUtil.readString(Constant.STRING_AUTH_TOKEN));
        user.setAutoLogin(SharedPrefUtil.readBoolean(Constant.STRING_AUTO_LOGIN));
        user.setNotification(SharedPrefUtil.readBoolean(Constant.STRING_NOTIFICATION));
        user.setNotification(SharedPrefUtil.readBoolean(Constant.STRING_SILENCE));
        return user;
    }
}
