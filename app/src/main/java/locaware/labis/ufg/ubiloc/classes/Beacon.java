package locaware.labis.ufg.ubiloc.classes;

public class Beacon {
    private int rssi;
    private String name;

    public Beacon(int rssi, String name) {
        this.rssi = rssi;
        this.name = name;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
