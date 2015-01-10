package info.jiangchuan.wrca;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class WRCAApplication extends Application{

    private static WRCAApplication mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private LruBitmapCache mLruBitmapCache;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized WRCAApplication getInstance() {
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
