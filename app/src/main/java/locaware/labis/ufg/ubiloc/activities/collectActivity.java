package locaware.labis.ufg.ubiloc.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.BluetoothUtils;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class collectActivity extends AppCompatActivity {


    //Activities elements
    Button mDetectButton;

    //Variables
    private BluetoothUtils btUtils;
    private Handler handler = new Handler();
    ArrayList<Beacon> discoveredDevices = new ArrayList<>();
    ArrayList<Beacon> referencesBeacons = new ArrayList<>();
    int beaconsQtdDetected = 0;
    House workingHouse = HouseBuffer.getHouseBuffer();


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

                    discoveredDevices.clear();

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

                }
            });


        }
    };

}
