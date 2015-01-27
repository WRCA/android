package info.jiangchuan.wrca;

import android.app.Application;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import info.jiangchuan.wrca.gcm.GCMService;
import info.jiangchuan.wrca.models.Event;
import info.jiangchuan.wrca.models.Notification;
import info.jiangchuan.wrca.models.User;
import info.jiangchuan.wrca.models.UserData;
import info.jiangchuan.wrca.util.SerializeUtil;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class WillowRidge extends Application{

    private static final String TAG = "WillowRidge" ;
    private static WillowRidge mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;

    private UserData userData = new UserData();
    GCMService gcmService;

    public UserData getUserData() {
        return userData;
    }

    public GCMService getGcmService() {
        return gcmService;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        gcmService = new GCMService(this);
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

}
