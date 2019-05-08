package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class House {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Room> rooms;

    public House(){
        users = new ArrayList<User>();
        rooms = new ArrayList<Room>();
    }

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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void addUserToArray(User user){
        users.add(user);
    }

    public void addRoomToArray(Room room){
        rooms.add(room);
    }
}
