package locaware.labis.ufg.ubiloc.classes;

public class Distance {
    private double theDistance;
    private String referBeacon;

    public Distance(double theDistance, String referBeacon) {
        this.theDistance = theDistance;
        this.referBeacon = referBeacon;
    }

    public double getTheDistance() {
        return theDistance;
    }

    public void setTheDistance(double theDistance) {
        this.theDistance = theDistance;
    }

    public String getReferBeacon() {
        return referBeacon;
    }

    public void setReferBeacon(String referBeacon) {
        this.referBeacon = referBeacon;
    }

    public String toString(){
        return "Distância ao beacon: " + getReferBeacon() + " é " + getTheDistance() + "m\n";
    }
}
