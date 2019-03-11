package locaware.labis.ufg.ubiloc.innerDatabase;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.activities.MainActivity;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Room;
import locaware.labis.ufg.ubiloc.classes.User;

public class Buffer {
    //TODO APAGAR ESTA VARIÁVEL DEPOIS
    private static final String TAG = "Debug";

    private static House houseBuffer = new House();

    public static House getHouseBuffer() {
        return houseBuffer;
    }

    public static void setHouseBuffer(House houseBuffer) {
        Buffer.houseBuffer = houseBuffer;
    }

    //TODO IMPLEMENTAR ESTE MÉTODO

    public static synchronized void loadBufferFromUsername(final String username){
        DatabaseReference reference = FbDatabase.getUsernameByReference(username);
        if(reference != null){
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    House settedHouse = dataSnapshot.getValue(House.class);
                    Buffer.setHouseBuffer(settedHouse);
                    settedHouse.toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }else{
            Log.d(TAG, "loadBufferFromUsername: Erro ao carregar a base de dados");
        }
    }
}
