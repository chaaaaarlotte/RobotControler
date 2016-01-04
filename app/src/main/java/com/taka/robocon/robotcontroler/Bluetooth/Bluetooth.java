package com.taka.robocon.robotcontroler.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class Bluetooth {
    private final String LOG_TITLE = "BLUETOOTH";

    BluetoothAdapter bluetoothAdapter = null;
    BluetoothDevice bluetoothDevice = null;
    BluetoothSocket bluetoothSocket;
    InputStream bluetoothIn;
    OutputStream bluetoothOut;
    RecieveThread bluetiithReciever;
    LineRecieveListener listener;

    public void setRecieveListener(LineRecieveListener l){
        listener = l;
    }

    // 接続
    public boolean connect(UUID uuid, String devName){
        boolean result =false;
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d(LOG_TITLE, "ADAPTER", null);
        if(bluetoothAdapter != null) {
            Set<BluetoothDevice> blueDev = bluetoothAdapter.getBondedDevices();
            Log.d(LOG_TITLE, "devices", null);
            for (BluetoothDevice dev: blueDev) {
                Log.d(LOG_TITLE, "DEVICE Name" + dev.getName());
                if (dev.getName().equals(devName)){
                    bluetoothDevice = dev;
                    Log.d(LOG_TITLE, "DEVICE FOUND", null);
                }
            }
        }

        if (bluetoothDevice != null) {
            Log.d(LOG_TITLE, "create Socket", null);
            try{
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
            }catch (IOException e){
                Log.e(LOG_TITLE, "create socket failed", e);
            }
        }

        if (bluetoothSocket != null) {
            InputStream trmpIn = null;
            OutputStream tempOut = null;
            Log.d(LOG_TITLE, "connect Socket", null);
            try{
                bluetoothSocket.connect();
            }catch (IOException e){

            }
        }

        return result;
    }

    public void close() {
        if(bluetoothSocket != null){
            Log.d(LOG_TITLE, "Socket Close request");
            try{
                bluetoothSocket.connect();
            }catch (IOException e){
                Log.e(LOG_TITLE, "Socket Close error");
            }
            bluetoothSocket = null;
            bluetoothDevice = null;
        }
        try{
            if(bluetiithReciever != null){
                bluetiithReciever.finish();
                bluetiithReciever.join();
                bluetiithReciever = null;
            }
        }catch (Exception e){
            Log.e(LOG_TITLE, "Reciever Thread Join error.");
        }
    }

    public void sendData(byte[] buf, int len){
        String val = "";
        for(int inx =0; inx < len; inx++){
            val += String.format("%02x",buf[inx]);
        }
        if((bluetoothSocket != null) && (bluetoothOut != null)){
            Log.e(LOG_TITLE, "Write data :" + len + "byte" + val);
            try{
                bluetoothOut.write(buf, 0, len);
            }catch (IOException e){
                Log.e(LOG_TITLE, "Write data errpr");
            }
        }
    }

    // 受信スレッド
    public class RecieveThread extends Thread {
        byte[] buffer = new byte[1024];
        int tempNum = 0;
        private boolean terminate = false;

        @Override
        public void run() {
            terminate = false;
            Log.e(LOG_TITLE, "RecieveThread started.");
            while (!terminate) {
                int numRead = readData();
                if (numRead > 0){
                    Log.e(LOG_TITLE, "RecieveThread : READ data. num:"
                            + numRead + ":" + buffer);
                    listener.lineRecieved(new String(buffer, 0, numRead));
                }else{
                    Log.d(LOG_TITLE, "RecieveThread : READ zero data");
                }
            }
            Log.d(LOG_TITLE, "RecieveThread : Terminating.");
        }

        // データの読み込み
        private int readData() {
            int numRead = 0;
            try {
                 numRead = bluetoothIn.read(buffer);
            }catch (IOException e) {
                numRead = 0;
                Log.e(LOG_TITLE, "Read Data Exception", e);
            }
            return numRead;
        }

        private void finish() {
            terminate = true;
        }
    }
}
