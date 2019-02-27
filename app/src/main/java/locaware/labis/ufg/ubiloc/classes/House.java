package locaware.labis.ufg.ubiloc.classes;

import java.util.ArrayList;

public class House {
    private String name;
    private ArrayList<Room> rooms;
    private int qtdRooms;
    private ArrayList<User> users;

    public House(){
        rooms = new ArrayList<>();
        users = new ArrayList<>();
    }

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

    public void setUserAtPosition(int position, User user){
        this.users.set(position,user);
    }

    public int getQtdRooms() {
        return qtdRooms;
    }

    public void setQtdRooms(int qtdRooms) {
        this.qtdRooms = qtdRooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
