package locaware.labis.ufg.ubiloc.classes;

public class Beacon {
    private int rssi;
    private String address;
    private Position beacon_position;

    public Beacon(int rssi, String address) {
        this.rssi = rssi;
        this.address = address;
    }

    public Beacon(int rssi, String address, Position beacon_position) {
        this.rssi = rssi;
        this.address = address;
        this.beacon_position = beacon_position;
    }

    public Beacon(){}

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Position getBeacon_position() {
        return beacon_position;
    }

    public void setBeacon_position(Position beacon_position) {
        this.beacon_position = beacon_position;
    }
}
