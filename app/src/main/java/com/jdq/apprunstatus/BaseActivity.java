package com.jdq.apprunstatus;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by jimi on 12/2/15.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
        AppForegroundStateManager.getInstance().onActivityVisible(this);
    }

    @Override
    protected void onStop() {
        AppForegroundStateManager.getInstance().onActivityNotVisible(this);
        super.onStop();
    }

}
