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
 * javaFX controller for the LevelComplete.fxml scene
 */
public class LevelCompletedController implements Initializable{

    @FXML
    Button nextLevel;

    /**
     * method called when the return to main menu button is pressed, changing the scene to llepsxov.fxml
     * @throws IOException
     */
    @FXML
    public void returnToMainMenu() throws IOException {
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    /**
     * method called when the retry level button is pressed, changing the scene to SpellingQuiz.fxml
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
     * method called when the next level button is pressed, updating the level and changing the scene to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void nextLevel() throws IOException {
        Level.nextLevel();
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/SpellingQuiz.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * method called when the play video button is pressed, changing the scene to SpellingQuiz.fxml
     * @throws IOException
     */
    @FXML
    public void playVideo() throws IOException {
        Level.nextLevel();
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/VideoScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * called after the quiz is completed and checks if the next level is accessible
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
