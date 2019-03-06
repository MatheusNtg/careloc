package locaware.labis.ufg.ubiloc.classes;

import android.bluetooth.BluetoothAdapter;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;

import locaware.labis.ufg.ubiloc.activities.collectActivity;

public class Utils {

    private static final String TAG = "Debug";
    private final long SCAN_TIME = 5000;
    Handler handler = new Handler();


    //Retorna se uma caixa de texto editável está vazia ou não
    public static Boolean isTextFieldEmpty(EditText editText){
        if(editText.getText().toString().equals("")){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public static Beacon getReferenceBeacon(ArrayList<Beacon> btDevices){
        Beacon reference = new Beacon();

        int rssiAverage = 0;
        int count = 0;

        //Ordena o array dos dispositivos descobertos do maior RSSI para o menor
        btDevices.sort(new Comparator<Beacon>() {
            @Override
            public int compare(Beacon o1, Beacon o2) {
                return o2.getRssi() - o1.getRssi();
            }
        });

        //Obtem o MAC do dispositivo com o maior RSSI
        String currentAddress = btDevices.get(0).getAddress();

        //Faz a média dos valores do beacon com maior RSSI
        for (Beacon b: btDevices) {
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

    public static void setReferenceBeacon(Beacon reference, Room room){
        room.addReferenceBeacon(reference);
    }
}
