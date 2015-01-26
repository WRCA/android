package info.jiangchuan.wrca;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.util.SerializeUtil;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class WillowRidge extends Application{

    private static WillowRidge mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;
    private List<Event> events;
    private List<Notification> notifications = new ArrayList<Notification>();

    public User getUser() {
        return user;
    }

    private User user = new User();

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }

    void loadPersistentDate() {
        List<Notification> tmp = (ArrayList<Notification>)SerializeUtil.deSerialize(Constant.FILE_SAVED_NOTIFICATIONS);
        if (tmp != null) {
            notifications.addAll(tmp);
        }

        List<Event> tmp2 = (ArrayList<Event>)SerializeUtil.deSerialize(Constant.FILE_SAVED_EVENTS);
        if (tmp2 != null) {
           events.addAll(tmp2);
        }
    }
    public static synchronized WillowRidge getInstance() {
        return mInstance;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    mLruBitmapCache);
        }

        return this.mImageLoader;
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public List<Event> getSavedEvents() {
        if (events == null) {
            events = new ArrayList<Event>();
        }
        return events;
    }
}
