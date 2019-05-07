package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class Room {
    private Beacon referenceBeacon;
    private String name;

    public Room(){}

    public Room(Beacon referenceBeacon, String name) {
        this.referenceBeacon = referenceBeacon;
        this.name = name;
    }

    public Beacon getReferenceBeacon() {
        return referenceBeacon;
    }

    public void setReferenceBeacon(Beacon referenceBeacon) {
        this.referenceBeacon = referenceBeacon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
