import java.net.*;
import java.io.*;
import java.util.*;

public class WhozuppServerThreadHandler {
    public static void main(String[] args) throws IOException {
        HashMap<Integer,ObjectOutputStream> clientOutputs = new HashMap<Integer,ObjectOutputStream>();

        if (args.length != 1) {
            System.err.println("Usage: java WhozuppServerThreadHandler <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        int id = 1;
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                Socket server = serverSocket.accept();
                System.out.println("New user connected. ID: " + id);
                (new WhozuppMessageThread(server, id, clientOutputs)).start();

                clientOutputs.put(id, new ObjectOutputStream(server.getOutputStream()));
                id++;
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
