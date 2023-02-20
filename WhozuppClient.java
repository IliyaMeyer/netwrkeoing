
import java.io.*;
import java.net.*;

/**
*  The client side class to connect to the server
*/
public class WhozuppClient {

   private Socket whozSocket;
   private int portNumber = 1234;
   private String hostName = "localHost";
   private ObjectOutputStream objOut;
   private ObjectInputStream objIn;

   public WhozuppClient() throws IOException {
      try {
         this.whozSocket = new Socket(hostName, portNumber);
         this.objOut = new ObjectOutputStream(whozSocket.getOutputStream());
         this.objIn = new ObjectInputStream(whozSocket.getInputStream());
      } catch(IOException e) {
         System.err.println("Couldn't get I/O for the connection to " +
                hostName);
         System.exit(1);
      }

   }
/**
*  This needs to be run right after connection is established to set the user
*  nickname (Might need to be run multiple times if invalid username is given)
*  @param   Nickname The nickname the user has selected
*
*  @return  True if the nickname is succesfully added. False if the nickname is taken
*/
   public boolean sendNickname(String Nickname)
   {
      try {
         WhozuppDataObject data = new WhozuppDataObject(2, 0, 0, Nickname, null);
         objOut.writeObject(data);
         //Waits for servr to check nickname and then reply
         data = (WhozuppDataObject) objIn.readObject();
         //Should probably use a different implementation
         if (data.type == 0) {
            return false;
         } else {
            return true;
         }
      } catch(IOException e) {
         System.err.println("Couldn't get I/O for the connection to " + hostName);
         System.exit(1);
      } catch(ClassNotFoundException e) {

      }
      return false;
   }
/**
*  Sends a message to the given user
*  @param   user  User that the message is to be sent to (0 for global message)
*  @param   message  Message to be sent
*
*  @return  True if message sent. False if it fails
*/
   public boolean sendMessage(int user, String message)
   {
   // Default message id set for now
      try {
      WhozuppDataObject data = new WhozuppDataObject(1, 0, user, message, null);
      objOut.writeObject(data);
      return true;
      } catch(IOException e) {
         return false;
      }
   }

/**
*  Waits for a message to be sent from the server and then returns
*  (might need to be put in a loop uncertian)
*  @return  The data object that was sent from the server should contain all
*           relavent information to know what to display where.
*/
   public WhozuppDataObject recieveMessage()
   {
      try {
         return (WhozuppDataObject) objIn.readObject();
      } catch(IOException e) {
         System.err.println("Couldn't get I/O for the connection to " + hostName);
         System.exit(1);
      } catch(ClassNotFoundException e) {

      }
      return null;
   }

/**
*  Disconnects client from server and closes the objectInput/Output streams
*/
   public void disconect()
   {
      try {
         objOut.close();
         objIn.close();
         whozSocket.close();
      } catch(IOException e) {
         System.err.println("Couldn't get I/O for the connection to " + hostName);
         System.exit(1);
      }
   }


 }

