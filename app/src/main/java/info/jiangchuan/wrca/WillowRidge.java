package info.jiangchuan.wrca;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class WillowRidge extends Application{

    private static WillowRidge mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;
    private List<Event> mSavedEvents;
    private List<Notification> notifications = new ArrayList<Notification>();

    public List<Notification> getNotifications() {
        return notifications;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
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
        if (mSavedEvents == null) {
            mSavedEvents = new ArrayList<Event>();
        }
        return mSavedEvents;
    }
}
