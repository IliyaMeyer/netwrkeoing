import java.util.*;
import java.io.*;

/**
*   This is the data struct that is used to send info between the client and
*   server (This file should be both on client and server side to decode the
*   data stream
*   @param type This is the type of data that is being sent
*               0: Invalid
*               1: Message
*               2: Set client nickname
*               3: User connected
*               4: User disconnected
*   @param id   Id of the message being sent
*   @param user Id of user for sending private message. If client-server then it is the recipient, if server-client then it is the sender
*   @param message The message to be sent/ or Client nickname if type 2
*   @param map  Hashmap of all connected clients sent with server message when
*               a user joins or disconnects
*/
public class WhozuppDataObject implements Serializable{
    public int type;
    public int id;
    public String user;
    public String message;
    public ArrayList<String> names;

    public WhozuppDataObject(int type, int id, String user, String message, ArrayList<String> names) {
        this.type = type;
        this.id = id;
        this.user = user;
        this.message = message;
        this.names = names;
    }
}
