package llepsxov.scenes.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import llepsxov.application.DataBase;
import llepsxov.application.Voxspell;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * is a javaFX controller class for the llepsxov.fxml scene, where the llepsxov.fxml scene represents the quiz's main menu.
 */
public class MainMenuController implements Initializable {

    // singleton DataBase object
    private DataBase _dataBase = DataBase.getInstance();


    /**
     * controls the logic for the new spelling quiz pane in the main menu, taking the user to a new scene SelectQuizSettings.fxml
     * @throws IOException
     */
    @FXML
    public void newSpellingQuizClicked() throws IOException {

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/SelectQuizSettings.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * controls the logic for the new review quiz pane in the main menu, taking the user to a new scene RevisionSettings.fxml
     * @throws IOException
     */
    public void reviewQuizClicked() throws IOException {
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/RevisionSettings.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    /**
     * controls the logic for the view statistics pane in the main menu, taking the user to a new scene ViewStatsScene.fxml
     * @throws IOException
     */
    public void viewStatisticsClicked() throws IOException {
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/ViewStatsScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();

    }

    /**
     * controls the logic for the clear statistics pane in the main menu, taking the user to a new scene ClearStatisticsScene.fxml
     * @throws IOException
     */
    public void clearStatisticsClicked() throws IOException {

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/ClearStatisticsScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }


    /**
     * is called when the program (VOXSPELL) is started, printing the saved files from the database
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        _dataBase.printSavedFIles();
    }
}
