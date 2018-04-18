package com.redmart.catalog.base;

import android.app.Application;

import com.redmart.catalog.BuildConfig;

import timber.log.Timber;


public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
