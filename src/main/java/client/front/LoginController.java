package client.front;

import client.back.Listener;
import common.data.User;
import common.interaction.Response;
import common.interaction.ResponseResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController implements ResponseListener {
    @FXML
    private TextField userTf;
    @FXML
    private PasswordField passTf;
    @FXML
    private Button loginBtn;
    @FXML
    private Button enterBtn;
    @FXML
    private VBox enterVbox;
    @FXML
    private VBox loginVbox;
    @FXML
    private Label statusLabel;

    private Parent root;
    private Stage stage;
    private Scene scene;




    public LoginController() {

    }

    private void printOutput() {
        System.out.println(userTf.getText());
        System.out.println(passTf.getText());
    }


    public void login(ActionEvent actionEvent) throws IOException {
        User user = new User(userTf.getText(), passTf.getText());
        Listener.sendAuth(user);

    }

    public void enter(ActionEvent actionEvent) {
        enterVbox.setVisible(false);
        loginVbox.setVisible(true);

    }

    public void switchScene() throws IOException {
    }

    @Override
    public void onResponseReceived(Response response) {
        if(response.getResponseResult().equals(ResponseResult.AUTH)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Operation successful: " + response.getResponseBody());
            alert.showAndWait();
            try {
                SceneController.changeSceneWithAnimation("mainPage.fxml");
            }catch (IOException e) {
                System.out.println(e.getMessage());
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Operation failed: " + response.getResponseBody());
            alert.showAndWait();
        }
    }
}

