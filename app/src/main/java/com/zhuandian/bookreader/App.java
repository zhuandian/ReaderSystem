package com.zhuandian.bookreader;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * desc :
 * author：xiedong
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this, "3b3a8173f7d02cc1222afb432eee0868");
    }
}
