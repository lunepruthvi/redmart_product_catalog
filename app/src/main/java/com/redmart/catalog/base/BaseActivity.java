package com.redmart.catalog.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.redmart.catalog.R;
import com.redmart.catalog.logger.Logger;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    public BroadcastReceiver connectivityChangeReceiver = null;
    public Snackbar networkSnackbar = null;
    protected Activity activity;
    ConnectivityReceiver connectivityReceiver = null;

    static public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public ConnectivityReceiver getConnectivityReceiver() {
        return connectivityReceiver;
    }

    public void setConnectivityReceiver(ConnectivityReceiver connectivityReceiver) {
        this.connectivityReceiver = connectivityReceiver;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        Logger.d3(TAG, "@onCreaate");
    }

    public void registerConnectivityChangeReceiver() {
        Logger.d3(TAG, "registerConnectivity");
        this.connectivityChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.v(TAG, "action: " + intent.getAction());
                Log.v(TAG, "component: " + intent.getComponent());
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String key : extras.keySet()) {
                        Log.v(TAG, "key [" + key + "]: " +
                                extras.get(key));
                    }

                    boolean isNoConnectionPresent = extras.getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY);
                    Logger.d3(TAG, "<1> isNoConnectionPresent: " + isNoConnectionPresent);
                    Logger.d3(TAG, "<2> " + isNetworkConnected());
                    if (!isNetworkConnected()) {
                        showSnackbar();
                        onNoNetwork();
                    } else {
                        if (networkSnackbar != null) {
                            networkSnackbar.dismiss();
                        }
                        onNetwork();
                    }
                } else {
                    Log.v(TAG, "no extras");
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(this.connectivityChangeReceiver, intentFilter);
    }

    public void unregisterConnectivityChangeReceiver() {
        if (this.connectivityChangeReceiver != null) {
            this.unregisterReceiver(this.connectivityChangeReceiver);
            //this.connectivityChangeReceiver = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Logger.d3(TAG, "@onPause");
        unregisterConnectivityChangeReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d3(TAG, "@onResume");
        if (/* !isNetworkConnected() && */ connectivityChangeReceiver != null) {
            Logger.d3(TAG, "Base - onResume- regester conntectivity reciever");
            registerConnectivityChangeReceiver();
        }
    }

    public void onNoNetwork() {
        Logger.d3(TAG, "Base- onNoNetwork");
    }

    public void onNetwork() {
        if (connectivityReceiver != null) {
            connectivityReceiver.onConnected();
        }
        Logger.d3(TAG, "Base- onNetwork");
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public void showNetworkSetting() {
        Intent intent = new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK);
        startActivity(intent);
    }

    public void showSnackbar() {
        View contentView = this.findViewById(android.R.id.content);
        networkSnackbar = Snackbar.make(contentView, "No Network!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Settings", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BaseActivity.this.showNetworkSetting();
                    }
                })
                .setActionTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        networkSnackbar.show();
    }

    public interface ConnectivityReceiver {
        void onConnected();
    }
}
