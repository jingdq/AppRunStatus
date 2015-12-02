package com.jdq.apprunstatus;

import android.app.Application;
import android.util.Log;

/**
 * Created by jimi on 12/2/15.
 */
public class MyApplication extends Application implements AppForegroundStateManager.OnAppForegroundStateChangeListener {

    @Override
    public void onCreate() {
        super.onCreate();
        AppForegroundStateManager.getInstance().addListener(this);
    }

    @Override
    public void onAppForegroundStateChange(AppForegroundStateManager.AppForegroundState newState) {
        if (AppForegroundStateManager.AppForegroundState.IN_FOREGROUND == newState) {
            // App just entered the foreground. Do something here!

            Log.e("aaa"," entered the foreground ");


        } else {
            // App just entered the background. Do something here!

            Log.e("aaa"," entered the background ");
        }
    }


}
