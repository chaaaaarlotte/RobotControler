package com.taka.robocon.robotcontroler;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.taka.robocon.robotcontroler.Bluetooth.LineRecieveListener;

public class BluetoothConnectionService extends Service implements LineRecieveListener {
    public BluetoothConnectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void lineRecieved(String rec) {

    }
}
