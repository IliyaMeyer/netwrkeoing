import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class WhozuppServerThreadHandler {
    public static void main(String[] args) throws IOException {
        HashMap<String,ObjectOutputStream> clientOutputs = new HashMap<String,ObjectOutputStream>();
        ArrayList<String> clients = new ArrayList<String>();
        Lock lock = new ReentrantLock();

        if (args.length != 1) {
            System.err.println("Usage: java WhozuppServerThreadHandler <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            while (listening) {
                Socket server = serverSocket.accept();
                System.out.println("New user connected: " + server);
                (new WhozuppMessageThread(server, clientOutputs, lock, clients)).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
