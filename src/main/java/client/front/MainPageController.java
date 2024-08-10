package client.front;

import client.back.Client;
import client.back.Listener;
import client.utility.RequieredInput;
import common.data.Chapter;
import common.data.Coordinates;
import common.data.MeleeWeapon;
import common.data.SpaceMarine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;


public class MainPageController {

    private final HashMap<String, RequieredInput> commandsToInput = new HashMap<>();

    @FXML
    private VBox mainVbox;
    @FXML
    private VBox idVbox;
    @FXML
    private VBox spaceMarineVbox;
    @FXML
    private ChoiceBox<String> commandChoice;
    @FXML
    private TextField chapterNameFld;
    @FXML
    private TextField coordXFld;
    @FXML
    private TextField hearthCountFld;
    @FXML
    private HBox commandHbox;
    @FXML
    private TextField chapterWrldFld;
    @FXML
    private Button confirmCommandBtn;
    @FXML
    private TextField nameFld;
    @FXML
    private TextField heightFld;
    @FXML
    private TextField idFld;
    @FXML
    private ChoiceBox<String> meleeWpnChoice;
    @FXML
    private TextField coordYFld;
    @FXML
    private TextField healthFld;
    @FXML
    private Button logoutBtn;


    @FXML
    public void initialize() {
        idVbox.managedProperty().bind(idVbox.visibleProperty());
        spaceMarineVbox.managedProperty().bind(spaceMarineVbox.visibleProperty());
        commandsToInput.put("help", RequieredInput.NOTHING);
        commandsToInput.put("info", RequieredInput.NOTHING);
        commandsToInput.put("add", RequieredInput.OBJECT);
        commandsToInput.put("show", RequieredInput.NOTHING);
        commandsToInput.put("update", RequieredInput.UPDATE);
        commandsToInput.put("remove_by_id", RequieredInput.ID);
        commandsToInput.put("exit", RequieredInput.NOTHING);
        commandsToInput.put("remove_at", RequieredInput.ID);
        commandsToInput.put("add_if_max", RequieredInput.OBJECT);
        commandsToInput.put("remove_greater", RequieredInput.OBJECT);
        commandsToInput.put("filter_by_chapter", RequieredInput.CHAPTER);
        commandsToInput.put("print_unique_heart_count", RequieredInput.NOTHING);
        commandsToInput.put("filter_less_than_health", RequieredInput.HEALTH);
        commandsToInput.put("execute", RequieredInput.NOTHING);
        commandsToInput.put("register", RequieredInput.NOTHING);
        commandsToInput.put("auth", RequieredInput.NOTHING);
        List<String> commandArrayList = new ArrayList<>(commandsToInput.keySet());
        ObservableList<String> commandList = FXCollections.observableList(commandArrayList);
        meleeWpnChoice.getItems().addAll(MeleeWeapon.nameArr());
        spaceMarineVbox.setVisible(false);
        idVbox.setVisible(false);

        // create a choiceBox
        commandChoice.getItems().addAll(commandList);

        commandChoice.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch (commandsToInput.get(newValue)) {
                    case ID:
                        idVbox.setVisible(true);
                        spaceMarineVbox.setVisible(false);
                        break;
                    case OBJECT:
                        spaceMarineVbox.setVisible(true);
                        idVbox.setVisible(false);
                        break;
                    case NOTHING:
                        spaceMarineVbox.setVisible(false);
                        idVbox.setVisible(false);
                        break;
                }
            }
        });


    }

    @FXML
    public void confirmCommand(ActionEvent actionEvent) throws IOException {
        Listener.sendCommand(commandChoice.getValue(), getSpaceMarine());

    }

    @FXML
    public void logout(ActionEvent event) {
        Client.userID = -1;
        try {
            SceneController.changeSceneWithAnimation("loginPage.fxml");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    public SpaceMarine getSpaceMarine() {
        SpaceMarine spaceMarine = new SpaceMarine(0L,
                nameFld.getText(),
                new Coordinates(Float.parseFloat(coordXFld.getText()), Double.parseDouble(coordYFld.getText())),
                ZonedDateTime.now(),
                Integer.parseInt(healthFld.getText()),
                Long.parseLong(hearthCountFld.getText()),
                Long.parseLong(heightFld.getText()),
                MeleeWeapon.valueOf(meleeWpnChoice.getValue().toUpperCase()),
                new Chapter(chapterNameFld.getText(), chapterWrldFld.getText()),
                Client.userID
        );
        return spaceMarine;
    }

}
