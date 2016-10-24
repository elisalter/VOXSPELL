package llepsxov.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import llepsxov.application.Voxspell;

/**
 * JavaFX controller for the RevisionComplete.fxml scene
 */
public class RevisionCompleteController {

    /**
     * method for the return to main menu button, switches scene to the main menu
     */
    @FXML
    public void returnMainMenu() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            //do nothing
        }
    }

    /**
     * method for the retry button, retrys the current revision quizz
     */
    public void retry() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            //do nothing
        }
    }
}
