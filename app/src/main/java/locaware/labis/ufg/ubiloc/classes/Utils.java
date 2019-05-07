package locaware.labis.ufg.ubiloc.classes;

import android.os.Handler;
import android.widget.EditText;

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
}
