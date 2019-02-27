package locaware.labis.ufg.ubiloc.classes;

import android.util.Log;
import android.widget.EditText;

public class Utils {

    private static final String TAG = "Debug";

    //Retorna se uma caixa de texto editável está vazia ou não
    public static Boolean isTextFieldEmpty(EditText editText){
        if(editText.getText().toString().equals("")){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
