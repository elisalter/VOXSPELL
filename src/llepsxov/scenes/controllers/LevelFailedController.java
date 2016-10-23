package llepsxov.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import llepsxov.application.Level;
import llepsxov.application.Voxspell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * javaFX controller class for the scene LevelFailedScene.fxml, scene that appears after failing a level
 * @Author Eli Salter
 */
public class LevelFailedController implements Initializable {

    @FXML
    private Button nextLevel; // disabled if level 10

    /**
     * controls the logic for the retry level button, when pressed the scene switches to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void retryLevel() throws IOException {

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/SpellingQuiz.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * controls the logic for the return to main menu button, when pressed the scene switches to llepsxov.fxml (our main menu)
     * @throws IOException
     */
    public void returnToMainMenu() throws IOException {
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void statisticsButtonClicked() throws IOException{
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/ViewStatsScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * called when the scene is opened, makes sure to disable the next level button if on level 10
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Level.getCurrentlevel() >= 10) {
            nextLevel.setDisable(true);
        }
    }
}
