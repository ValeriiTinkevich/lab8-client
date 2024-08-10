package client.front;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public final class SceneController {
    private static Stage primaryStage;

    private SceneController() {
    }

    public static void setPrimaryStage(Stage primaryStage) {
        SceneController.primaryStage = primaryStage;
    }

    public static void changeScene(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(SceneController.class.getClassLoader().getResource(fxml));
        primaryStage.getScene().setRoot(pane);
    }

    public static void changeSceneWithAnimation(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneController.class.getClassLoader().getResource(fxml));
        Parent newSceneRoot = loader.load();  // Load the new scene root

        StackPane rootPane = new StackPane(); // Use StackPane for transitions
        Parent currentRoot = primaryStage.getScene().getRoot();
        rootPane.getChildren().addAll(currentRoot, newSceneRoot);

        primaryStage.setScene(new Scene(rootPane, primaryStage.getScene().getWidth(), primaryStage.getScene().getHeight()));

        // Set the new root to be initially invisible
        newSceneRoot.setOpacity(0);

        // Fade out the current root
        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), currentRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> {
            rootPane.getChildren().remove(currentRoot); // Remove the old root
            // Fade in the new root
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), newSceneRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });

        fadeOut.play();
    }
}
