import java.net.*;
import java.io.*;
import java.util.*;

public class WhozuppMessageThread extends Thread {
    private Socket socket = null;
    private int clientId;
    private HashMap<Integer,ObjectOutputStream> clientOutputs;

    public WhozuppMessageThread(Socket socket, int clientId, HashMap<Integer,ObjectOutputStream> clientOutputs) {
        super("WhozuppMessageThread");
        this.socket = socket;
        this.clientId = 0;
        this.clientOutputs = clientOutputs;
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            WhozuppDataObject inputData, outputData;
            while ((inputData = (WhozuppDataObject) in.readObject()) != null) {
                outputData = new WhozuppDataObject(1, 0, clientId, inputData.message, null);

                if (inputData.id == 0) {
                    for (Map.Entry<Integer,ObjectOutputStream> entry : clientOutputs.entrySet()) {
                        entry.getValue().writeObject(outputData);
                    }
                } else {
                    clientOutputs.get(inputData.id).writeObject(outputData);
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
