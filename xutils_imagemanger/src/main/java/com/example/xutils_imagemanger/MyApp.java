package com.example.xutils_imagemanger;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/12/29.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
