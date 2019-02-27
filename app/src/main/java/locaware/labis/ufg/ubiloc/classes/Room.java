package locaware.labis.ufg.ubiloc.classes;

public class Room {
    private double width;
    private double height;
    private String name;

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
}
