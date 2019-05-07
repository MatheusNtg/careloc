package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class House {
    private String name;
    private ArrayList users = new ArrayList<User>();
    private ArrayList rooms = new ArrayList<Room>();

    public House(){}

    public House(String name, ArrayList users, ArrayList rooms) {
        this.name = name;
        this.users = users;
        this.rooms = rooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void addUserToArray(User user){
        users.add(user);
    }

    public void addRoomToArray(Room room){
        rooms.add(room);
    }
}
