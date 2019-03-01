package locaware.labis.ufg.ubiloc.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import locaware.labis.ufg.ubiloc.R;

public class collectActivity extends AppCompatActivity {


    //Variables
    BluetoothAdapter bluetoothAdapter;

    //Consts
    public final int REQUEST_ENABLE_BT = 1;
    public final String TAG = "Debug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);


        checkBLEOnDevice();
        Log.d(TAG, "~ Dispositivo aceita BLE");

        startBluetoothAdapter();
        Log.d(TAG, "~ Adaptador Bluetooth iniciado");

        enableBT();
        Log.d(TAG, "~ Bluetooth ligado");

        



    }

    public void startBluetoothAdapter(){
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    public void checkBLEOnDevice(){
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE não é suportado neste dispositivo", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void enableBT(){
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
}
