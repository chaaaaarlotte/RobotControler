package com.taka.robocon.robotcontroler;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.taka.robocon.robotcontroler.Bluetooth.Bluetooth;

public class RobotControler extends Activity {

    private BluetoothAdapter bluetoothAdapter;
    private final int REQUEST_ENABLE_BLUETOOTH = 1;

    private Bluetooth bluetooth;

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup);
        //MapDraw mapDraw = new MapDraw(this);
        //setContentView(mapDraw);

        bluetooth = new Bluetooth();

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter.equals(null)){
            Toast.makeText(this, "Bluetooth非対応端末", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void  onStart(){
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!bluetoothAdapter.isEnabled()) {
            Intent blutoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(blutoothOn, REQUEST_ENABLE_BLUETOOTH);
        } else {
            setContentView(R.layout.activity_robotcontroler);
            SurfaceView touchArea = (SurfaceView) findViewById(R.id.touch_area);
            Log.d("Width & Heith", String.valueOf(touchArea.getWidth()) + String.valueOf(touchArea.getHeight()));
        }

        startService(new Intent(RobotControler.this,
                BluetoothConnectionService.class));
        bindService(new Intent(RobotControler.this,
                BluetoothConnectionService.class), );
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
        if (resultCode == RESULT_CANCELED){
            if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
                finish();
            }
        }
    }
}
