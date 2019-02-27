package locaware.labis.ufg.ubiloc.innerDatabase;

import java.util.ArrayList;

import locaware.labis.ufg.ubiloc.classes.House;

public class Buffer {
    private static ArrayList<House> houseBuffer = new ArrayList<>();

    public static ArrayList<House> getHouseBuffer() {
        return houseBuffer;
    }

    public static void setHouseBuffer(ArrayList<House> houseBuffer) {
        Buffer.houseBuffer = houseBuffer;
    }

    public static void addHouse(House house){
        houseBuffer.add(house);
    }

    public static House getLastHouse(){
        return houseBuffer.get(houseBufferSize() - 1);
    }

    public static int houseBufferSize(){
        return houseBuffer.size();
    }
}
