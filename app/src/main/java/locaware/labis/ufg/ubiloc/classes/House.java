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

    public void setUserAtArrayPosition(int position, User user){
        this.users.add(position,user);
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

    public void addRoomAtLastIndex(Room room){
        this.rooms.add(this.rooms.size(),room);
    }

    public Room getLastRoom(){
        return this.rooms.get(this.rooms.size() - 1);
    }

    public String toString(){
        return "Nome da casa: " + name + "\n" +
                "Quantidade de quartos: " + qtdRooms + "\n" +
                "Quarto 0: " + rooms.get(0).getName() + "\n" +
                "Beacons de referências: " + rooms.get(0).getReferencesBeacons();

    }
}
