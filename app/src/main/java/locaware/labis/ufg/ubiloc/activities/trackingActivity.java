package locaware.labis.ufg.ubiloc.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.altbeacon.beacon.distance.CurveFittedDistanceCalculator;
import org.altbeacon.beacon.distance.ModelSpecificDistanceCalculator;
import org.w3c.dom.Text;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.R;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.BluetoothUtils;
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

public class trackingActivity extends AppCompatActivity {

    //Consts
    private Context context;
    private final String TAG = "Debug";

    //Activity elements
    private TextView distanceTextView;
    private Button distanceButton;

    //Vars
    private BluetoothUtils bluetoothUtils;
    private String beaconAddress = "Erro";

    ArrayList<Beacon> referencesBeacons = Buffer.getHouseBuffer().getLastRoom().getReferencesBeacons();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        bluetoothUtils = new BluetoothUtils(this);

        //Turn on the bt if isn't on

        if(!bluetoothUtils.getBluetoothAdapter().isEnabled()){
            bluetoothUtils.enableBT();
        }

        //Setting up the activity elements
        distanceButton   = findViewById(R.id.distanceButton);
        distanceTextView = findViewById(R.id.distanceTextView);
        context = this;

        //Setting up the bt utils
        bluetoothUtils = new BluetoothUtils(this);

        scanLEDevices(bluetoothUtils.getBluetoothAdapter());

        //Listener
        distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDistance(distanceTextView,0);
            }
        });


    }


    private void scanLEDevices(final BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter.isEnabled()){
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
                    Beacon currentBeacon = new Beacon(rssi,device.getAddress());
                    calculateDistance(referencesBeacons,currentBeacon);
                }
            });


        }
    };


    private void showDistance(TextView textView, double distance){
        scanLEDevices(bluetoothUtils.getBluetoothAdapter());
        textView.setText(String.format("%.2f m", distance));
    }


    private void calculateDistance(ArrayList<Beacon> reference, Beacon current){
        double theDistance = -2;
        //Percorre a lista até achar o beacon que está sendo escaneado
        for (Beacon ref: reference) {
            if (ref.getAddress().equals(current.getAddress())) {
                double division = (double) current.getRssi() / ref.getRssi();
                beaconAddress = current.getAddress();

                //Teste
                ModelSpecificDistanceCalculator modelSpecificDistanceCalculator
                        = new ModelSpecificDistanceCalculator(context,"teste");

                theDistance = modelSpecificDistanceCalculator.calculateDistance(ref.getRssi(),(double) current.getRssi());
                showDistance(distanceTextView,theDistance);
            }
        }
    }


}
