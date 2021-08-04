package com.danny.coremodule;

import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class CoreActivity extends AppCompatActivity
        implements InternetBroadcastReceiver.OnInternetConnectionChangeListener {
    private IntentFilter intentFilter;
    private InternetBroadcastReceiver receiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onCreate");
        intentFilter = new IntentFilter();
        intentFilter.addAction(InternetBroadcastReceiver.CONNECTIVITY_ACTION);
        receiver = new InternetBroadcastReceiver();
        receiver.setOnInternetConnectionChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onResume");
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onPause");
        unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onRestart");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onPostResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Activity Lifecycle", getClass().getSimpleName() + "=======> onDestroy");
    }

    @Override
    public void onDisconnected() {
        onInternetDisconnect();
    }

    @Override
    public void onConnected() {
        onInternetConnect();
    }

    public abstract void onInternetDisconnect();

    public abstract void onInternetConnect();
}
