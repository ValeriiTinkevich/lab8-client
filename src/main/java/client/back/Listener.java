package client.back;

import client.front.App;
import client.front.ResponseListener;
import common.data.SpaceMarine;
import common.data.User;
import common.interaction.Request;
import common.interaction.Response;
import common.interaction.ResponseAuth;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Listener implements Runnable{

    private Socket socket;
    public String hostname;
    public int port;
    private static ObjectOutputStream oos;
    private static ObjectInputStream is;
    private InputStream input;
    private OutputStream outputStream;
    private ResponseListener responseListener;


    public Listener(String hostname, int port, ResponseListener responseListener) {
        this.hostname = hostname;
        this.port = port;
        this.responseListener = responseListener;
    }

    public void run() {
        try {
            socket = new Socket(hostname, port);
            outputStream = socket.getOutputStream();
            oos = new ObjectOutputStream(outputStream);
            input = socket.getInputStream();
            is = new ObjectInputStream(input);

        } catch (IOException e) {
            App.logger.severe("Could not connect to the server!");
        }
        App.logger.info("Connection accepted " + socket.getInetAddress() + ":" + socket.getPort());

        try {
            App.logger.info("Connected to the server successfully!");
            while (socket.isConnected()) {
                Response response = (Response) is.readObject();
                if(response != null) {
                    App.logger.info("Message recieved: " + response.getResponseBody() + " Result: " + response.getResponseResult());

                    if (responseListener != null) {
                        final Response finalResponse = response;
                        Platform.runLater(() -> responseListener.onResponseReceived(finalResponse));
                    }

                    if (response instanceof ResponseAuth) {
                        Client.userID = ((ResponseAuth) response).getUserID();
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            App.logger.severe("Error!");
        }

    }

    public static void sendAuth(User user) throws IOException {
        oos.writeObject(new Request("auth", user, Client.userID));
        oos.flush();
    }

    public static void sendCommand(String command, SpaceMarine spaceMarine) throws IOException {
        oos.writeObject(new Request(command, spaceMarine, Client.userID));
        oos.flush();
    }
}
