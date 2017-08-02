package com.example.xiangmu;

import android.app.Application;

import org.xutils.x;

/**
 * Created by fanyishuo on 2017/8/2.
 */

public class Add extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
