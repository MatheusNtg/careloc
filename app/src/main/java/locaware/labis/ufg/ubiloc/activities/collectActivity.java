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

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.BluetoothUtils;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

public class collectActivity extends AppCompatActivity {


    //Activities elements
    Button mDetectButton;

    //Variables
    private BluetoothUtils btUtils;
    private Handler handler = new Handler();
    ArrayList<Beacon> devices = new ArrayList<>();
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
        btUtils = new BluetoothUtils(this);


        btUtils.checkBLEOnDevice();

        btUtils.enableBT();

        btUtils.checkBTPermissions();

        mDetectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "~ Iniciando descoberta de dispositivos");
                scanLEDevices(btUtils.getBluetoothAdapter());
            }
        });





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
                    Beacon referencia = Utils.getReferenceBeacon(devices);

                    //Coloca este beacon de referência no quarto em questão
                    Utils.setReferenceBeacon(referencia,Buffer.getHouseBuffer().getLastRoom());

                    String message = "~ MAC: " + referencia.getAddress() + " Média de potência: " + referencia.getRssi();

                    Toast.makeText(context,message,Toast.LENGTH_LONG).show();

                    //Verifica se os 3 beacons de um quarto já foram detectados
                    if(beaconsQtdDetected == 2){
                        //Inicia a próxima activity
                        FbDatabase.writeBeacons(Buffer.getHouseBuffer(), Buffer.getHouseBuffer().getLastRoom().getReferencesBeacons());
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

}
