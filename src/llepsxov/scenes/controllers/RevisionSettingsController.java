package llepsxov.scenes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import llepsxov.application.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * JavaFX controller class for the RevisionSettings.fxml file in Voxspell
 */
public class RevisionSettingsController implements Initializable {

    ArrayList<ToggleButton> myButtons = new ArrayList<ToggleButton>();
    RevisionQuiz _revisionQUiz = RevisionQuiz.getInstance();
    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    @FXML
    Button continueButton;

    @FXML
    ToggleButton level1, level2, level3, level4, level5, level6, level7, level8, level9, level10;

    @FXML
    Label noLevelsToReview, pleaseSelectALevel;

    @FXML
    private ComboBox selectVoice;

    //================================================================================================================
    // Methods controling the logic for when a button is selected in the revision quiz settings screen below
    // the level is set, and the continue button is enabled (warning message also disappears)

    @FXML
    public void level1Pressed(ActionEvent event) {

        Level.setLevel(1);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level2Pressed() {

        Level.setLevel(2);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level3Pressed() {

        Level.setLevel(3);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level4Pressed() {

        Level.setLevel(4);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level5Pressed() {
        Level.setLevel(5);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level6Pressed() {
        Level.setLevel(6);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level7Pressed() {
        Level.setLevel(7);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level8Pressed() {
        Level.setLevel(8);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level9Pressed() {
        Level.setLevel(9);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    @FXML
    public void level10Pressed() {
        Level.setLevel(10);
        pleaseSelectALevel.setVisible(false);
        continueButton.setDisable(false);
    }

    //=================================================================================================================

    /**
     * adds all buttons to an array list of buttons
     */
    public void addButtons() {
        myButtons.addAll(Arrays.asList(level1, level2, level3, level4,level5, level6, level7, level8,level9,level10));
    }

    /**
     * switches scene to a new review quiz
     * @throws IOException
     */
    @FXML
    public void continueToQuiz() throws IOException {

        SpellingLogic._isNewQuiz = false;// review quiz

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/SpellingQuiz.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }

    /**
     * switches scene to the main menu
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
     * changes the festival voice
     */
    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    /**
     * called on start up of the RevisionSettings.fxml scene, sets the continuation button to false before a level is
     * selected
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButtons(); // add all the buttons to arraylist

        continueButton.setDisable(true);

        noLevelsToReview.setVisible(true);
        pleaseSelectALevel.setVisible(false);
        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);

        // for every level, see if there's words to review
        for (int i = 0; i < 10; i++) {
            boolean levelExists = _revisionQUiz.checkAnyWords(i + 1);

            if (!_revisionQUiz.checkAnyWords(i + 1)) {
                    myButtons.get(i).setDisable(true);
            } else {
                noLevelsToReview.setVisible(false);
                pleaseSelectALevel.setVisible(true);
            }
        }
    }
}
