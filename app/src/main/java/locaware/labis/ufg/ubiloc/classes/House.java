package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class House {
    ArrayList users = new ArrayList<User>();
    ArrayList rooms = new ArrayList<Room>();

    public House(){}

    public House(ArrayList users, ArrayList rooms) {
        this.users = users;
        this.rooms = rooms;
    }

    public ArrayList getUsers() {
        return users;
    }

    public void setUsers(ArrayList users) {
        this.users = users;
    }

    public ArrayList getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList rooms) {
        this.rooms = rooms;
    }
}
