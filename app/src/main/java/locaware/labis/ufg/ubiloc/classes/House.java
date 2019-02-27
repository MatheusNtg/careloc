package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class House {
    private ArrayList<Room> rooms;
    private ArrayList<User> users;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }
}
