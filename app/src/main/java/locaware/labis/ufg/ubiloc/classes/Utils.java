package locaware.labis.ufg.ubiloc.classes;

import android.os.Handler;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Comparator;

public class Utils {

    private static final String TAG = "Teste";
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

    public static void cleanEditText(EditText editText){
        editText.setText("");
    }

    public static void sortDiscoveredDevices(ArrayList<Beacon> devices){
        devices.sort(new Comparator<Beacon>() {
            @Override
            public int compare(Beacon o1, Beacon o2) {
                return o2.getRssi() - o1.getRssi();
            }
        });
    }
}
