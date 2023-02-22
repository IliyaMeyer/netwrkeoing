import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.locks.*;

public class WhozuppMessageThread extends Thread {
    private Socket socket = null;
    private String clientId;
    private HashMap<String,ObjectOutputStream> clientOutputs;
    private ArrayList<String> clients;
    private Lock lock;

    public WhozuppMessageThread(Socket socket, HashMap<String,ObjectOutputStream> clientOutputs, Lock lock, ArrayList<String> clients) {
        super("WhozuppMessageThread");
        this.socket = socket;
        this.clientOutputs = clientOutputs;
        this.lock = lock;
        this.clients = clients;
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            WhozuppDataObject inputData, outputData;

            while (true)
            {
                if ((inputData = (WhozuppDataObject) in.readObject()) == null)
                    return;

                if (inputData.type != 2 || clientOutputs.containsKey(inputData.user)) {
                    lock.lock();
                    out.writeObject(new WhozuppDataObject(3,0,null, null, null));
                    lock.unlock();
                    continue;
                }

                clientId = inputData.user;
                System.out.println("Client connected: " + clientId);

                lock.lock();
                clientOutputs.put(inputData.user, out);
                clients.add(inputData.user);
                out.writeObject(new WhozuppDataObject(1,0,null, null, clients));

                for (Map.Entry<String,ObjectOutputStream> entry : clientOutputs.entrySet()) {
                    if (entry.getKey() == clientId)
                        continue;
                    entry.getValue().writeObject(new WhozuppDataObject(3, 0, clientId, null, clients));
                }

                lock.unlock();
                break;
            }

            while ((inputData = (WhozuppDataObject) in.readObject()) != null) {
                outputData = new WhozuppDataObject(1, 0, clientId, inputData.message, null);
                System.out.println("From: " + clientId + " To: " + inputData.user + " " + inputData.message);
                if (inputData.user == null) {
                    lock.lock();
                    for (Map.Entry<String,ObjectOutputStream> entry : clientOutputs.entrySet()) {
                        // Comment out later:
                        if (entry.getKey() == clientId)
                            continue;
                        System.out.println(entry.getValue());
                        entry.getValue().writeObject(outputData);
                    }
                    lock.unlock();
                } else {
                    lock.lock();
                    clientOutputs.get(inputData.id).writeObject(outputData);
                    lock.unlock();
                }
            }
            socket.close();
        } catch (IOException e) {
            lock.lock();
            clients.remove(clientId);
            clientOutputs.remove(clientId);

            try {
                for (Map.Entry<String,ObjectOutputStream> entry : clientOutputs.entrySet()) {
                    entry.getValue().writeObject(new WhozuppDataObject(4, 0, clientId, null, clients));
                }
            } catch (IOException f) {
                f.printStackTrace();
            }
            lock.unlock();
            System.out.println("Client disconnected: " + clientId);

            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
