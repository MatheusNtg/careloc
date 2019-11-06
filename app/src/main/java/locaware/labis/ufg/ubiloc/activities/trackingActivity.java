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

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;

import org.altbeacon.beacon.distance.ModelSpecificDistanceCalculator;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

public class trackingActivity extends AppCompatActivity {

    //Consts
    private Context context;
    private final String TAG = "Debug";
    private final long SCAN_PERIOD = 1000;

    //Activity elements
    private TextView mpositionTextView;

    //Vars
    private BluetoothUtils bluetoothUtils;
    private House workingHouse = HouseBuffer.getHouseBuffer();
    private ArrayList<Beacon> scanPackets = new ArrayList<Beacon>();
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    protected Room actualRoom = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking);

        bluetoothUtils = new BluetoothUtils(this);

        //Turn on the bt if isn't on

        if(!bluetoothUtils.getBluetoothAdapter().isEnabled()){
            bluetoothUtils.checkBTPermissions();
            bluetoothUtils.enableBT();
        }

        //Setting up the activity elements
        context          = this;
        mpositionTextView = findViewById(R.id.positionTextView);

        //Setting up the bt utils
        bluetoothUtils = new BluetoothUtils(this);

        scanLEDevices(bluetoothUtils.getBluetoothAdapter());

        //Listeners


    }


    private void scanLEDevices(final BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.startLeScan(leScanCallBack);


            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(scanPackets.size() > 0){
                         Utils.sortDiscoveredDevices(scanPackets);
                         actualRoom = Utils.searchRoomByBeacon(workingHouse.getRooms(),scanPackets.get(0));
                    }
                    if(actualRoom != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Utils.updateTextView(mpositionTextView,trackingActivity.this.actualRoom.getName());
                            }
                        });
                    }
                    scanPackets.clear();
                }
            },0,SCAN_PERIOD);
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
                    scanPackets.add(new Beacon(device.getAddress(),rssi));
                }
            });


        }
    };
}
