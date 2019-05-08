package locaware.labis.ufg.ubiloc.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.BluetoothUtils;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.ActivityBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class collectActivity extends AppCompatActivity {


    //Activities elements
    private Button mDetectButton;
    private TextView mgotoTextView;

    //Variables
    private BluetoothUtils btUtils;
    private Handler handler = new Handler();
    private ArrayList<Beacon> discoveredDevices = new ArrayList<>();
    private House workingHouse = HouseBuffer.getHouseBuffer();
    private int numberOfWorkingRoom = 0;
    private ArrayList<Room> rooms = workingHouse.getRooms();

    //Consts
    private final int REQUEST_ENABLE_BT = 1;
    private final String TAG = "Debug";
    private final Context context = this;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        //Setting up the activity elemets
        mDetectButton = findViewById(R.id.detectButton);
        btUtils = new BluetoothUtils(this);
        mgotoTextView = findViewById(R.id.gotoTextView);


        btUtils.checkBLEOnDevice();

        btUtils.enableBT();

        btUtils.checkBTPermissions();

        mgotoTextView.setText("Vá para: " + rooms.get(numberOfWorkingRoom).getName());

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
                    Toast.makeText(context,"Descoberta encerrada",Toast.LENGTH_LONG).show();
                    bluetoothAdapter.stopLeScan(leScanCallBack);

                    //Todo add the reference beacon discovered by app
                    //Sort the discovered devices
                    Utils.sortDiscoveredDevices(discoveredDevices);

                    //Pick the first of the array (the stronger one) and put as the reference for that room
                    rooms.get(numberOfWorkingRoom).setReferenceBeacon(discoveredDevices.get(0));

                    numberOfWorkingRoom++;
                    //Clean the discovered devices list
                    discoveredDevices.clear();

                    //If the user has covered all house, then write in database and start the tracking activity
                    if(numberOfWorkingRoom == ActivityBuffer.getRoomsToCreate()){
                        FbDatabase.writeHouse(workingHouse);
                        Intent intent = new Intent(context, trackingActivity.class);
                        startActivity(intent);
                    }else{
                        mgotoTextView.setText("Vá para: " + rooms.get(numberOfWorkingRoom).getName());
                    }
                }
            },SCAN_PERIOD);

            Log.d(TAG, "~ scanLEDevices: Descoberta iniciada");
            Toast.makeText(context,"Descoberta iniciada",Toast.LENGTH_LONG).show();
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
                    discoveredDevices.add(new Beacon(device.getAddress(),rssi));
                }
            });


        }
    };

}
