package com.nakiha.release.ControlModule;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity {

    private ForceOffLineReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String activityName = getClass().getSimpleName();
        if (getSupportActionBar()!=null
                && !activityName.equals("ControlRemoteCarActivity"))
            getSupportActionBar().hide();
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ForceOffLineReceiver.BroadcastTAG);
        mReceiver = new ForceOffLineReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mReceiver != null){
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
