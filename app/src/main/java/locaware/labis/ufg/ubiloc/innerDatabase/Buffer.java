package locaware.labis.ufg.ubiloc.innerDatabase;

import android.app.Activity;
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


    /*
    * Carrega o buffer a partir de um usuário e ao final do processo inicia uma activity
    * @param username Usuário para buscar no BD
    * @param bufferLoadedCallback função para executar após o carregamento do buffer
    * @param activity Atividade a ser iniciada*/
    public static void loadBufferFromUsername(final String username,final bufferLoadedCallback bufferLoadedCallback){
        FbDatabase.getHouseReferenceByUsername(username, new FbDatabase.HouseLoadedCallback() {
            @Override
            public void onSuccess(DatabaseReference reference) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        House settedHouse = dataSnapshot.getValue(House.class);
                        Buffer.setHouseBuffer(settedHouse);
                        bufferLoadedCallback.callback();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public interface bufferLoadedCallback{
        void callback();
    }
}
