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
    private String beaconAddress = "Erro";
    private ArrayList<Distance> distancePacks = new ArrayList<>();
    private double[] position = new double[2];

    ArrayList<Beacon> referencesBeacons = UsernameBuffer.getHouseBuffer().getLastRoom().getReferencesBeacons();



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
        positionButton   = findViewById(R.id.positionButton);
        positionTextView = findViewById(R.id.positionTextView);
        context          = this;

        //Setting up the bt utils
        bluetoothUtils = new BluetoothUtils(this);

        scanLEDevices(bluetoothUtils.getBluetoothAdapter());

        //Listeners
        positionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPosition(positionTextView,position);
            }
        });

        //Just for debug TODO Erase this later
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Debug---->" + distancePacks);
            }
        },0,PERIOD);

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
                    Beacon currentBeacon = new Beacon(rssi,device.getAddress());
                    calculateDistance(referencesBeacons,currentBeacon);
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


    private void calculateDistance(ArrayList<Beacon> reference, Beacon current){
        double distance = -2;
        //Percorre a lista até achar o beacon que está sendo escaneado
        for (Beacon ref: reference) {
            if (ref.getAddress().equals(current.getAddress())) {
                beaconAddress = current.getAddress();

                //Teste
                ModelSpecificDistanceCalculator modelSpecificDistanceCalculator
                        = new ModelSpecificDistanceCalculator(context,"teste");

                distance = modelSpecificDistanceCalculator.calculateDistance(ref.getRssi(),(double) current.getRssi());
                Distance theDistance = new Distance(distance,ref.getAddress());
                if(distancePacks.size() < 3){
                    //Só adiciona esta distância se ela não estiver no pacote
                    if(!isOnPacket(theDistance)){
                        Utils.addDistance(distancePacks,theDistance);
                    }
                }else{
                    calculatePosition(distancePacks);
                    showPosition(positionTextView,position);
                    distancePacks.clear();
                }
            }
        }
    }

    private void calculatePosition(ArrayList<Distance> distancePacks){
        ArrayList<Beacon> sampleReferenceBeacons = UsernameBuffer.getHouseBuffer().getLastRoom().getReferencesBeacons();

        //TODO fazer a posição ser adquirida a partir da posição de cada instância do beacon no quarto
        double[] pos_b1 = {sampleReferenceBeacons.get(0).getBeacon_position().getX() ,
                sampleReferenceBeacons.get(0).getBeacon_position().getY()};
        double[] pos_b2 = {sampleReferenceBeacons.get(1).getBeacon_position().getX(),
                sampleReferenceBeacons.get(1).getBeacon_position().getY()   };
        double[] pos_b3 = {sampleReferenceBeacons.get(2).getBeacon_position().getX(),
                sampleReferenceBeacons.get(2).getBeacon_position().getY()};

        Utils.organizeDistancePacks(distancePacks,sampleReferenceBeacons);

        double rad_b1 = distancePacks.get(0).getTheDistance();
        double rad_b2 = distancePacks.get(1).getTheDistance();
        double rad_b3 = distancePacks.get(2).getTheDistance();

        double[][] beacons_positions = new double[][] { pos_b1, pos_b2, pos_b3};

        double[] distances = new double[] {rad_b1,rad_b2,rad_b3};

        NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(beacons_positions, distances), new LevenbergMarquardtOptimizer());
        LeastSquaresOptimizer.Optimum optimum = solver.solve();

        // the answer
        position = optimum.getPoint().toArray();

        //This put the user position in Firebase
        FbDatabase.updateUserPosition(new Position(position[0], position[1]));

        //This put the beacons distance in Firebase  (just for test)
        FbDatabase.updateBeaconsDistance(distancePacks);

        Log.d(TAG, "calculatePosition: Position:" + position[0] + ", " + position[1] + "\n");
    }

    private boolean isOnPacket(Distance distance){
        for(Distance d: distancePacks){
            if(d.getReferBeacon().equals(distance.getReferBeacon())){
                return true;
            }
        }

        return false;
    }


}
