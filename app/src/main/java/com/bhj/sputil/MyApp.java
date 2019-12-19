package com.bhj.sputil;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MMKV.initialize(this);
    }
}
