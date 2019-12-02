package com.bhj.sputil;

import android.app.Application;

import com.bhj.sputil_lib.SPUtils;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SPUtils.init(this);
    }
}
