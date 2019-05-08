package locaware.labis.ufg.ubiloc.innerDatabase;

public class UsernameBuffer {

    public static String usernameFlag;

    public static void setUsernameFlag(String flag) { UsernameBuffer.usernameFlag = flag; }

    public static String getCurrentUsername() { return usernameFlag; }

}
