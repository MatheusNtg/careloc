package locaware.labis.ufg.ubiloc.innerDatabase;

public class ActivityBuffer {
    private static int roomsToCreate;

    public static void setRoomsToCreate(int qtdRooms){
        roomsToCreate = qtdRooms;
    }

    public static int getRoomsToCreate(){
        return roomsToCreate;
    }

}
