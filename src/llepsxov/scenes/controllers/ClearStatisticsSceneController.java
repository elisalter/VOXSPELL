package llepsxov.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import llepsxov.application.DataBase;
import llepsxov.application.RevisionQuiz;
import llepsxov.application.Voxspell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * A javaFX controller class for the ClearStatisticsSceneController.fxml
 */
public class ClearStatisticsSceneController implements Initializable{

    // Stores a referenence to the singleton DataBase and RevisionQuiz objects
    DataBase _db = DataBase.getInstance();
    RevisionQuiz _rv = RevisionQuiz.getInstance();


    // FXML GUI component fields
    @FXML
    Label yesLabel;

    @FXML
    Label noLabel;

    @FXML
    Button mainMenuButton;

    @FXML
    Button yesButton;

    @FXML
    Button noButton;


    /**
     * called on startup of the ClearStatisticsSceneController.fxml, accessed via the main menu.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //certain buttons should only be visible after the user makes a decision
        yesLabel.setVisible(false);
        noLabel.setVisible(false);
        mainMenuButton.setVisible(false);
        mainMenuButton.disarm();
    }


    /**
     * clears the statistics when the user presses yes and gives the option to return to the main menu
     */
    public void yesPressed(){
        yesLabel.setVisible(true);

        _db.clearStats();
        _rv.clearFailed();

        yesButton.setVisible(false);
        noButton.setVisible(false);

        mainMenuButton.arm();
        mainMenuButton.setVisible(true);



    }


    /**
     * does not clear any statistics, offerst the option to return to the main menu
     */
    public void noPressed(){

        noLabel.setVisible(true);


        yesButton.setVisible(false);
        noButton.setVisible(false);

        mainMenuButton.arm();
        mainMenuButton.setVisible(true);

    }

    /**
     * controls the return to the main menu after the user makes a decision on whether or not they should clear statistics
     * @throws IOException
     */
    public void mainMenuPressed() throws IOException{
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml")); //llepsxov.fxml is the main menu scene
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

}
