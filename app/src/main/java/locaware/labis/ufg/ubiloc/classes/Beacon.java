package locaware.labis.ufg.ubiloc.classes;

public class Beacon {
    String mac_address;

    public Beacon(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }
}
