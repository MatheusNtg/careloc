package locaware.labis.ufg.ubiloc.classes;

public class User {
    Room position;
    String name;

    public User(){}

    public User(Room position, String name) {
        this.position = position;
        this.name = name;
    }

    public Room getPosition() {
        return position;
    }

    public void setPosition(Room position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
