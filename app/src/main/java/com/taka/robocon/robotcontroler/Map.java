package com.taka.robocon.robotcontroler;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Map extends Activity {

    BluetoothAdapter bluetoothAdapter;
    private final int REQUEST_ENABLE_BLUETOOTH = 1;

    private int touchFlag = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private void sendSyncBroadcast() {
        Intent intent = new Intent();
        intent.setAction("Bluetooth");

        IntentFilter filter = new IntentFilter();
        filter.addAction("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.equals(null)){
            Toast.makeText(this, "Bluetooth非対応端末", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!bluetoothAdapter.isEnabled()){
            Intent blutoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(blutoothOn, REQUEST_ENABLE_BLUETOOTH);
        }else {
            setContentView(R.layout.activity_map);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("TouchEvent", "X" + event.getX() + ",Y" + event.getY());

        if (event.getAction() == MotionEvent.ACTION_DOWN || touchFlag == 0){
            Toast.makeText(this, "X:" + event.getX() + " Y:" + event.getY(), Toast.LENGTH_SHORT).show();
            touchFlag = 1;
        }

        if (event.getAction() == MotionEvent.ACTION_UP){
            touchFlag = 0;
        }
        return  true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
                setContentView(R.layout.activity_map);
            }
        }else {
            if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
                finish();
            }
        }
    }
}
