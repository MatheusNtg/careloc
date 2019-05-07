package locaware.labis.ufg.ubiloc.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import locaware.labis.ufg.ubiloc.classes.Utils;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;
import locaware.labis.ufg.ubiloc.innerDatabase.UsernameBuffer;

public class trackingActivity extends AppCompatActivity {

    //Consts
    private Context context;
    private final String TAG = "Debug";
    private final long PERIOD = 1500;

    //Activity elements
    private TextView positionTextView;
    private Button positionButton;

    //Vars
    private BluetoothUtils bluetoothUtils;



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

        //Setting up the bt utils
        bluetoothUtils = new BluetoothUtils(this);

        scanLEDevices(bluetoothUtils.getBluetoothAdapter());

        //Listeners


    }


    private void scanLEDevices(final BluetoothAdapter bluetoothAdapter){
        if(bluetoothAdapter.isEnabled()){
            bluetoothAdapter.startLeScan(leScanCallBack);
        }else{
//            Toast.makeText(context,"Bluetooth desligado, por favor, ligue o bluetooth", Toast.LENGTH_SHORT);
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

    private void showPosition(TextView text, double[] position){
        if(position.length != 0) {
            text.setText("x: " + String.format("%.2f", position[0]) +
                    "y: " + String.format("%.2f",position[1]));
        }
    }







}
