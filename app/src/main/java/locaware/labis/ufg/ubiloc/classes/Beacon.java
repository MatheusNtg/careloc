package locaware.labis.ufg.ubiloc.classes;

public class Beacon {
    private String mac_address;
    private int rssi;

    public Beacon(String mac_address){
        this.mac_address = mac_address;
    }

    public Beacon(String mac_address,int rssi) {
        this.mac_address = mac_address;
        this.rssi        = rssi;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    @Override
    public String toString() {
        return "MAC: " + this.mac_address + " - rssi: " + this.rssi + "\n";
    }
}
