
import java.io.*;
import java.net.*;

public class WhozuppClient {

   private Socket whozSocket;
   private int portNumber = 1234;
   private String hostName = "localHost";
   private ObjectOutputStream objOut;
   private ObjectInputStream objIn;

   public WhozuppClient(String Nickname) throws IOException {
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

   public boolean sendNickname(String Nickname)
   {

   }
   public boolean sendMessage()
   {
      return true;
   }

   public String[] getNames()
   {
      return new String[1];
   }


 }

