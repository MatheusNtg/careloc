package locaware.labis.ufg.ubiloc.innerDatabase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import locaware.labis.ufg.ubiloc.Database.FbDatabase;
import locaware.labis.ufg.ubiloc.classes.House;

public class HouseBuffer {

    private static final String TAG = "Debug";

    private static House houseBuffer = new House();

    public static House getHouseBuffer() {
        return houseBuffer;
    }

    public static void setHouseBuffer(House houseBuffer) {
        HouseBuffer.houseBuffer = houseBuffer;
    }

    public static void loadHouseBufferFromUsername(final String username,final bufferLoadedCallback bufferLoadedCallback){
        FbDatabase.getHouseReferenceByUsername(username, new FbDatabase.HouseLoadedCallback() {
            @Override
            public void onSuccess(DatabaseReference reference) {
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        House settedHouse = dataSnapshot.getValue(House.class);
                        HouseBuffer.setHouseBuffer(settedHouse);
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
