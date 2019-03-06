package locaware.labis.ufg.ubiloc.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.User;

public class FbDatabase {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void writeTest(House house){
        mDatabase.child(house.getName()).setValue(house);
    }

}
