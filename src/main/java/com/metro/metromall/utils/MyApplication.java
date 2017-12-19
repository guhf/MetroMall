package com.metro.metromall.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by guhf on 2017/11/23.
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        //获取Context
        context = getApplicationContext();
    }

    //返回
    public static Context getContext(){
        return context;
    }
}
