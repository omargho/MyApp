package com.omar.og.myapplication;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by OG on 10/11/2015.
 */
//this class is generated to make just 1 instance is responsable of communication with the internet
//and it connecting with internet as long as the application is running
public class VolleySingelton {
    private static VolleySingelton sInstance = null;//na3mlouha bech elli bech yesta3melha mouch lezem 3lih y3ayyat lel constructeur
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleySingelton() {
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());//we need to give him the  application context instead of activity's context
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String, Bitmap> cache = new LruCache<>((int) Runtime.getRuntime().maxMemory() / 1024 / 8);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    public static VolleySingelton getInstance() {// ~ getter
        if (sInstance == null) {
            sInstance = new VolleySingelton();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

}
