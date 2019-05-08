package locaware.labis.ufg.ubiloc.Database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import locaware.labis.ufg.ubiloc.activities.trackingActivity;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.innerDatabase.HouseBuffer;

public class FbDatabase {

    //TODO APAGAR ESTA VARIÁVEL DEPOIS
    private static final String TAG = "Debug";

    //Const
    private static final String USERNAMES_PATH = "users";
    //Variables
    private static DatabaseReference usernameByReference = null;



    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TOP_PARENT_HOUSE = "Houses";

    public static void writeHouse(House house){
        mDatabase.child(TOP_PARENT_HOUSE)
                .child(house.getName()).setValue(house);
    }

    public static void checkTheUsername(final Context context, final String username){
        DatabaseReference houses = mDatabase.child(TOP_PARENT_HOUSE);
        houses.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Query query;
                for (DataSnapshot d: dataSnapshot.getChildren()) {
                    query = d.child("users").getRef().orderByChild("name").equalTo(username);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.getValue() != null){
                                HouseBuffer.loadHouseBufferFromUsername(username, new HouseBuffer.bufferLoadedCallback() {
                                    @Override
                                    public void callback() {
                                        Intent intent = new Intent(context,trackingActivity.class);
                                        context.startActivity(intent);
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private static void loadReferenceByUserName(final String username, final HouseLoadedCallback callback){
        final DatabaseReference reference = mDatabase.child(TOP_PARENT_HOUSE);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot houses: dataSnapshot.getChildren()) {
                    for (DataSnapshot users: houses.child("users").getChildren()) {
                        if(users.child("name").getValue().toString().equals(username)){
                            usernameByReference = houses.getRef();
                            callback.onSuccess(usernameByReference);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public static void getHouseReferenceByUsername(String username, HouseLoadedCallback callback){
        loadReferenceByUserName(username,callback);
    }

    public interface HouseLoadedCallback{
        void onSuccess(DatabaseReference reference);
    }
}
