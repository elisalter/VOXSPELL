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
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import llepsxov.application.Festival;
import llepsxov.application.Level;
import llepsxov.application.Spelling_Logic;
import llepsxov.application.Voxspell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by eli on 21/09/16.
 * Controls the logic for the scene after the 'New Quiz button' or 'Review  Quiz button' is selected
 */
public class SelectQuizSettingsController implements Initializable {


    ArrayList<ToggleButton> myButtons = new ArrayList<ToggleButton>();

    public void addButtons() {
        myButtons.addAll(Arrays.asList(level1, level2, level3, level4,level5, level6, level7, level8,level9,level10));
    }

    int _levelUnlocked;

    @FXML
    Button continueButton;

    @FXML
    ToggleButton level1;

    @FXML
    ToggleButton level2;

    @FXML
    ToggleButton level3;

    @FXML
    ToggleButton level4;

    @FXML
    ToggleButton level5;

    @FXML
    ToggleButton level6;

    @FXML
    ToggleButton level7;

    @FXML
    ToggleButton level8;

    @FXML
    ToggleButton level9;

    @FXML
    ToggleButton level10;

    @FXML
    private ComboBox voiceChoiceBox;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    @FXML
    public void level1Pressed(ActionEvent event) {
        Level.setLevel(1);
        highlightButton(level1);
        _levelUnlocked = 1;
        continueButton.setDisable(false);
    }

    @FXML
    public void level2Pressed() {
        highlightButton(level2);
        Level.setLevel(2);
        _levelUnlocked = 2;
        continueButton.setDisable(false);
    }

    @FXML
    public void level3Pressed() {
        highlightButton(level3);
        Level.setLevel(3);
        _levelUnlocked = 3;
        continueButton.setDisable(false);
    }

    @FXML
    public void level4Pressed() {
        highlightButton(level4);
        Level.setLevel(4);
        _levelUnlocked = 4;
        continueButton.setDisable(false);
    }

    @FXML
    public void level5Pressed() {
        highlightButton(level5);
        Level.setLevel(5);
        _levelUnlocked = 5;
        continueButton.setDisable(false);
    }

    @FXML
    public void level6Pressed() {
        highlightButton(level6);
        Level.setLevel(6);
        _levelUnlocked = 6;
        continueButton.setDisable(false);
    }

    @FXML
    public void level7Pressed() {
        highlightButton(level7);
        Level.setLevel(7);
        _levelUnlocked = 7;
        continueButton.setDisable(false);
    }

    @FXML
    public void level8Pressed() {
        highlightButton(level8);
        Level.setLevel(8);
        _levelUnlocked = 8;
        continueButton.setDisable(false);
    }

    @FXML
    public void level9Pressed() {
        highlightButton(level9);
        Level.setLevel(9);
        _levelUnlocked = 9;
        continueButton.setDisable(false);
    }

    @FXML
    public void level10Pressed() {
        highlightButton(level10);
        Level.setLevel(10);
        _levelUnlocked = 10;
        continueButton.setDisable(false);
    }

    @FXML
    public void continueToQuiz() throws IOException {

        Level.setUnlockedlevel(_levelUnlocked);
        Spelling_Logic._isNewQuiz = true;

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/SpellingQuiz.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }

    @FXML
    public void returnToMainMenu() throws IOException {

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();





    }

    @FXML
    public void voiceChanging() {
        if (voiceChoiceBox.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (voiceChoiceBox.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButtons();

        voiceChoiceBox.setValue(Festival.voice());
        voiceChoiceBox.setItems(voiceList);

        continueButton.setDisable(true);


        if (Level.getUnlockedlevel() != 0) {
            for (int i = 0; i < 10; i++) {
                if (i + 1 > Level.getUnlockedlevel()) {
                    myButtons.get(i).setDisable(true);
                }
            }
        }

    }

    public void highlightButton(ToggleButton pressedBtn){

        for(ToggleButton b : myButtons){

            if (!b.equals(pressedBtn)){

                b.setOpacity(0.50);

            } else {

                b.setOpacity(1.00);

            }

        }
    }
}
