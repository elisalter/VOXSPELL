package llepsxoV;

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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 22/09/16.
 */
public class RevisionSettingsController implements Initializable {

    ArrayList<ToggleButton> myButtons = new ArrayList<ToggleButton>();

    RevisionQuiz _revisionQUiz = RevisionQuiz.getInstance();

    public void addButtons() {
        myButtons.addAll(Arrays.asList(level1, level2, level3, level4,level5, level6, level7, level8,level9,level10));
    }

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
    private ComboBox selectVoice;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    @FXML
    public void level1Pressed(ActionEvent event) {

        Level.setLevel(1);
    }

    @FXML
    public void level2Pressed() {

        Level.setLevel(2);
    }

    @FXML
    public void level3Pressed() {

        Level.setLevel(3);
    }

    @FXML
    public void level4Pressed() {

        Level.setLevel(4);
    }

    @FXML
    public void level5Pressed() {
        Level.setLevel(5);
    }

    @FXML
    public void level6Pressed() {
        Level.setLevel(6);
    }

    @FXML
    public void level7Pressed() {
        Level.setLevel(7);
    }

    @FXML
    public void level8Pressed() {
        Level.setLevel(8);
    }

    @FXML
    public void level9Pressed() {
        Level.setLevel(9);
    }

    @FXML
    public void level10Pressed() {
        Level.setLevel(10);
    }

    @FXML
    public void continueToQuiz() throws IOException {

        Spelling_Logic._isNewQuiz = false;

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("SpellingQuiz.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();


    }

    @FXML
    public void returnToMainMenu() throws IOException {
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addButtons();

        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);


        continueButton.setDisable(true);


        for (int i = 0; i < 10; i++) {
            boolean levelExists = _revisionQUiz.checkAnyWords(i + 1);

            if (!_revisionQUiz.checkAnyWords(i + 1)) {
                    myButtons.get(i).setDisable(true);
            } else {
                continueButton.setDisable(false);
            }
        }



    }
}
