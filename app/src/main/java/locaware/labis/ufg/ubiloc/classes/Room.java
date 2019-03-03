package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class Room {
    private double width;
    private double height;
    private String name;
    private ArrayList<Beacon> referencesBeacons;

    public Room(double width, double height, String name) {
        this.width = width;
        this.height = height;
        this.name = name;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Beacon> getReferencesBeacons() {
        return referencesBeacons;
    }

    public void setReferencesBeacons(ArrayList<Beacon> referencesBeacons) {
        this.referencesBeacons = referencesBeacons;
    }

    public void addReferenceBeacon(Beacon b){
        referencesBeacons.add(b);
    }
}
