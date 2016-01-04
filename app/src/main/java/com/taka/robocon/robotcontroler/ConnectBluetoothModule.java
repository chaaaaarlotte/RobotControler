package com.taka.robocon.robotcontroler;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.taka.robocon.robotcontroler.Bluetooth.Bluetooth;

public class ConnectBluetoothModule extends Service {
    private BroadcastReceiver receiver;

    // Bluetooth 関連
    Bluetooth bluetooth;

    @Override
    public void onCreate() {
        super.onCreate();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("action1")) {
                    Intent i = new Intent();
                    i.setAction("action2");

                    Log.d("", "Start Service");
                    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcastSync(i);
                    Log.d("", "End Service");
                }
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction("action1");
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(receiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(receiver);
    }
}
