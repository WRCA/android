package info.jiangchuan.wrca.util;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.Constant;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.models.User;

/**
 * Created by jiangchuan on 1/27/15.
 */
public class PersisUtil {
    private static final String TAG = "" ;

    static public void read(User user) {
        List<Notification> notifications = user.getNotifications();
        List<Event> events = user.getEvents();
        notifications.clear();
        user.getEvents().clear();
        List<Notification> tmp = (ArrayList<Notification>) SerializeUtil.deSerialize(Constant.FILE_SAVED_NOTIFICATIONS);
        if (tmp != null) {
            Log.d(TAG, "notif:" + Integer.toString(notifications.size()));
            notifications.addAll(tmp);
        }

        List<Event> tmp2 = (ArrayList<Event>)SerializeUtil.deSerialize(Constant.FILE_SAVED_EVENTS);
        if (tmp2 != null) {
            Log.d(TAG, "events:" + Integer.toString(tmp2.size()));
            events.addAll(tmp2);
        }

        user.setAutoLogin(SharedPrefUtil.readBoolean(Constant.STRING_AUTO_LOGIN));
        user.setToken(SharedPrefUtil.readString(Constant.STRING_AUTH_TOKEN));
        user.setNotification(SharedPrefUtil.readBoolean(Constant.STRING_NOTIFICATION));
        user.setEmail(SharedPrefUtil.readString(Constant.STRING_LOGIN_EMAIL));
    }

    static public void clear(User user) {
        user.getNotifications().clear();
        user.getEvents().clear();
    }
    static public void write(User user) {
        SerializeUtil.serialize(Constant.FILE_SAVED_NOTIFICATIONS, user.getNotifications());
        SerializeUtil.serialize(Constant.FILE_SAVED_EVENTS, user.getEvents());
        SharedPrefUtil.writeString(Constant.STRING_AUTH_TOKEN, user.getToken());
        SharedPrefUtil.writeString(Constant.STRING_LOGIN_EMAIL, user.getEmail());
//        SharedPrefUtil.writeBoolean(Constant.STRING_AUTO_LOGIN, user.isAutoLogin());
    }

}
