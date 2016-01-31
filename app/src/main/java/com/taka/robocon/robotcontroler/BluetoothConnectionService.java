package com.taka.robocon.robotcontroler;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.taka.robocon.robotcontroler.Bluetooth.Bluetooth;
import com.taka.robocon.robotcontroler.Bluetooth.LineRecieveListener;

import java.util.UUID;

public class BluetoothConnectionService extends Service implements LineRecieveListener {
    private final UUID SERVICE_UUID
            = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private final String DEVICE_NAME ="HC-06"; //接続先のデバイス名

    public Bluetooth bluetooth;
    private Handler handler;
    private  String cacheMessage;
    private  String editCacheMessage;


    public BluetoothConnectionService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();

        bluetooth = new Bluetooth();
        bluetooth.setRecieveListener(this);
        bluetooth.connect(SERVICE_UUID, DEVICE_NAME);
        handler = new Handler();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        bluetooth.close();
    }

    public void lineRecieved(String recievedMessage) {
        if(!recievedMessage.isEmpty()){
            cacheMessage = recievedMessage;
        }

        // 文字列の終わりが終端記号であった場合に受信完了とみなす
        editCacheMessage = cacheMessage.substring(cacheMessage.length() -1);
        if ((editCacheMessage.equals("\r")) || editCacheMessage.equals("\n")) {
            cacheMessage = cacheMessage.trim();
            if (!cacheMessage.isEmpty()) {
                // 受信完了後の処理
                
            }
        }
    }
}
