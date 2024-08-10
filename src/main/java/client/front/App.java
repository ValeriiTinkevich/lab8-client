package client.front;

import client.back.Listener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Logger;


public class App extends Application {
    public static final String PS1 = "$ ";
    public static final Logger logger = Logger.getLogger(App.class.getName());
    public static int UID = -1;
    private static final int MAX_RECONNECTION_ATTEMPTS = 6;
    private static String host = "localhost";
    private static int port = 65444;





    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("loginPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        SceneController.setPrimaryStage(stage);
        stage.setScene(scene);
        stage.setTitle("Hello");
        LoginController controller = loader.getController();
        Listener listener = new Listener(host, port, controller);
        new Thread(listener).start();
        stage.show();
    }

    public static void main(String[] args) throws InterruptedException {
        launch();
    }


}