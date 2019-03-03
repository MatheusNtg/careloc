package locaware.labis.ufg.ubiloc.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import locaware.labis.ufg.ubiloc.R;

public class collectActivity extends AppCompatActivity {


    //Variables
    BluetoothAdapter bluetoothAdapter;

    //Consts
    private final int REQUEST_ENABLE_BT = 1;
    private final String TAG = "Debug";
    private final Context context = this;


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

        Log.d(TAG, "~ Checking BT permissions");
        checkBTPermissions();

        Log.d(TAG, "~ Iniciando descoberta de dispositivos");
        scanLEDevices(bluetoothAdapter);



    }

    private void startBluetoothAdapter(){
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
    }

    private void checkBLEOnDevice(){
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE não é suportado neste dispositivo", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void enableBT(){
        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    private void scanLEDevices(BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter.isEnabled()){
            Log.d(TAG, "~ scanLEDevices: Descoberta iniciada");
            bluetoothAdapter.startLeScan(leScanCallBack);
        }else{
            Toast.makeText(context,"Bluetooth desligado, por favor, ligue o bluetooth", Toast.LENGTH_SHORT);
        }
    }

    private BluetoothAdapter.LeScanCallback leScanCallBack = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "Dispositivo encontrado: " + device.getName() + "RSSI: " + rssi);
                }
            });


        }
    };


    /**
     * This method is required for all devices running API23+
     * Android must programmatically check the permissions for bluetooth. Putting the proper permissions
     * in the manifest is not enough.
     *
     * NOTE: This will only execute on versions > LOLLIPOP because it is not needed otherwise.
     */
    private void checkBTPermissions() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if (permissionCheck != 0) {

                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001); //Any number
            }
        }else{
            Log.d(TAG, "checkBTPermissions: No need to check permissions. SDK version < LOLLIPOP.");
        }
    }
}
