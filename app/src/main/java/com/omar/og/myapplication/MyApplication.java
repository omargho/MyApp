package com.omar.og.myapplication;

import android.app.Application;
import android.content.Context;

/**
 * Created by OG on 10/11/2015.
 */
public class MyApplication extends Application {
    public static final String KEY_TOMATO="54wzfswsa4qmjg8hjwa64d4c";
    private static MyApplication sInstance;

    public void onCreate(){//first thing executed in the app
        super.onCreate();
        sInstance=this;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }


    public static MyApplication getInstance() {
        return sInstance;
    }
}
