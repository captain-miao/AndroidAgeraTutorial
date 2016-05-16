package com.github.captain_miao.agera.tutorial;

import android.app.Application;
import android.content.Context;

/**
 * @author YanLu
 * @since 16/5/16
 */
public class AppAgera extends Application{

    private static AppAgera instance;

    public static AppAgera getInstance() {
        return instance;
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
