package locaware.labis.ufg.ubiloc.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.classes.Beacon;
import locaware.labis.ufg.ubiloc.classes.House;
import locaware.labis.ufg.ubiloc.classes.User;

public class FbDatabase {
    private static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public static void writeHouse(House house){
        mDatabase.child(house.getName()).setValue(house);
    }

    public static void writeBeacons(House house, ArrayList<Beacon> referencesBeacon){
        int indexOfLastRoom = house.getRooms().indexOf(house.getLastRoom());
        mDatabase.child(house.getName()).child("rooms")
                .child(String.valueOf(indexOfLastRoom))
                .child("Reference Beacons").setValue(referencesBeacon);
    }

}
