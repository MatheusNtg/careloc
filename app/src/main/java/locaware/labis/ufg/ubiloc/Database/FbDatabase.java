package locaware.labis.ufg.ubiloc.Database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;

import locaware.labis.ufg.ubiloc.activities.MainActivity;
import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.Position;
import locaware.labis.ufg.ubiloc.classes.User;
import locaware.labis.ufg.ubiloc.innerDatabase.Buffer;

public class FbDatabase {

    //TODO APAGAR ESTA VARIÁVEL DEPOIS
    private static final String TAG = "Debug";

    //Const
    private static final String USERNAMES_PATH = "users";
    //Variables
    private static boolean result;
    private static DatabaseReference usernameByReference = null;



    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private static final String TOP_PARENT_HOUSE = "Houses";

    public static void writeHouse(House house){
        mDatabase.child(TOP_PARENT_HOUSE)
                .child(house.getName()).setValue(house);
    }

    public static void hasTheUsername(final String username, final HasTheUserCallback hasTheUserCallback, final Context context){
        final DatabaseReference reference = mDatabase.child(TOP_PARENT_HOUSE);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d.child("users").child("0").child("name").getValue().toString().equals(username)) {
                            hasTheUserCallback.callback(username);
                            return;
                        }
                    }
                    Toast.makeText(context,"Usuário não encontrado",Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onDataChange: Base de dados está vazia");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public interface HasTheUserCallback{
        void callback(String username);
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

    public static void updateUserPosition(Position position){
        User currentUser = new User(Buffer.getCurrentUsername(),position);

        //Todo Tornar isto escalável
        mDatabase.child(TOP_PARENT_HOUSE)
                .child(Buffer.getHouseBuffer().getName())
                .child("users")
                .child("0")
                .child("position")
                .setValue(currentUser.getPosition());

    }
}
