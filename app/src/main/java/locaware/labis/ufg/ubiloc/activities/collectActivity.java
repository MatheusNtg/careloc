package locaware.labis.ufg.ubiloc.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

public class collectActivity extends AppCompatActivity {


    //Activities elements
    Button mDetectButton;

    //Variables
    BluetoothAdapter bluetoothAdapter;
    private Handler handler = new Handler();
    ArrayList<Beacon> devices = new ArrayList<>();
    Buffer buffer = new Buffer();
    int beaconsQtdDetected = 0;

    //Consts
    private final int REQUEST_ENABLE_BT = 1;
    private final String TAG = "Debug";
    private final Context context = this;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        //Setting up the activity elemets
        mDetectButton = findViewById(R.id.detectButton);


        checkBLEOnDevice();
        Log.d(TAG, "~ O dispositivo aceita BLE");

        startBluetoothAdapter();
        Log.d(TAG, "~ Adaptador Bluetooth iniciado");

        enableBT();
        Log.d(TAG, "~ Bluetooth ligado");

        Log.d(TAG, "~ Checking BT permissions");
        checkBTPermissions();

        mDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "~ Iniciando descoberta de dispositivos");
                scanLEDevices(bluetoothAdapter);
            }
        });





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


    private void scanLEDevices(final BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter.isEnabled()){

            //Para a descoberta de dispostivos após o tempo determinado no SCAN_PERIOD
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "~ Parando descoberta de dispositivos");
                    bluetoothAdapter.stopLeScan(leScanCallBack);

                    //Gera objeto Beacon de referência a 1m
                    Beacon referencia = getReferenceBeacon(devices);

                    //Coloca este beacon de referência no quarto em questão
                    setReferenceBeacon(referencia,Buffer.getLastHouse().getLastRoom());

                    String message = "~ MAC: " + referencia.getAddress() + " Média de potência: " + referencia.getRssi();

                    Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                    //Verifica se os 3 beacons de um quarto já foram detectados
                    if(beaconsQtdDetected == 2){
                        //Inicia a próxima activity
                        Intent intent = new Intent(context,trackingActivity.class);
                        startActivity(intent);
                    }else{
                        beaconsQtdDetected++;
                    }
                }
            },SCAN_PERIOD);

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
                    //Adiciona os dispositivos encontrados no array list dos devices
                    devices.add(new Beacon(rssi,device.getAddress()));
                }
            });


        }
    };


    private Beacon getReferenceBeacon(ArrayList<Beacon> btDevices){
        Beacon reference = new Beacon();

        int rssiAverage = 0;
        int count = 0;

        //Ordena o array dos dispositivos descobertos do maior RSSI para o menor
        devices.sort(new Comparator<Beacon>() {
            @Override
            public int compare(Beacon o1, Beacon o2) {
                return o2.getRssi() - o1.getRssi();
            }
        });

        //Obtem o MAC do dispositivo com o maior RSSI
        String currentAddress = devices.get(0).getAddress();

        //Faz a média dos valores do beacon com maior RSSI
        for (Beacon b: devices) {
            if(b.getAddress().equals(currentAddress)){
                rssiAverage += b.getRssi();
                count++;
            }else{
                break;
            }
        }

        rssiAverage = rssiAverage/count;

        reference.setAddress(currentAddress);
        reference.setRssi(rssiAverage);

        btDevices.clear();

        return reference;
    }

    public void setReferenceBeacon(Beacon reference, Room room){
        room.addReferenceBeacon(reference);
    }


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
