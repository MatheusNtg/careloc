package locaware.labis.ufg.ubiloc.classes;

public class Beacon {
    private int rssi;
    private String address;

    public Beacon(int rssi, String address) {
        this.rssi = rssi;
        this.address = address;
    }

    public Beacon(){};

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
}
