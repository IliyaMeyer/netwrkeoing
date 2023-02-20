import java.util.*;
import java.io.*;

/**
*   This is the data struct that is used to send info between the client and
*   server (This file should be both on client and server side to decode the
*   data stream
*   @param type This is the type of data that is being sent
*               0: Server message
*               1: Client message
*               2: Set client nickname
*   @param id   Id of the message being sent
*   @param user Id of user for sending private message. If client-server then it is the recipient, if server-client then it is the sender
*   @param message The message to be sent/ or Client nickname if type 2
*   @param map  Hashmap of all connected clients sent with server message when
*               a user joins or disconnects
*/
public class WhozuppDataObject implements Serializable{
    public int type;
    public int id;
    public int user;
    public String message;
    public HashMap<Integer, String> map;

    public WhozuppDataObject(int type, int id, int user, String message, HashMap<Integer, String> map) {
        this.type = type;
        this. id = id;
        this.user = user;
        this.message = message;
        this.map = map;
    }
}
