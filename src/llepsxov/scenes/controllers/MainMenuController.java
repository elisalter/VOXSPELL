package llepsxov.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import llepsxov.application.DataBase;
import llepsxov.application.DesktopApi;
import llepsxov.application.Scores;
import llepsxov.application.Voxspell;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.FileChooser.*;

/**
 * is a javaFX controller class for the llepsxov.fxml scene, where the llepsxov.fxml scene represents the quiz's main menu.
 */
public class MainMenuController implements Initializable {

    // singleton DataBase object
    private DataBase _dataBase = DataBase.getInstance();
    private Scores _scores = Scores.getInstance();
    public String defaultSpellingListPath = "./src/llepsxov/spelling/NZCER-spelling-lists.txt";

    @FXML
    private Text errorReadingFile, selectedListAlert;

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
     * controls the logic for the clear statistics pane in the main menu, taking the user to a new scene ClearStatisticsSceneController.fxml
     * @throws IOException
     */
    public void clearStatisticsClicked() throws IOException {

        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/ClearStatisticsScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * Opens README.md file, if file can't open sucessfully an exception is thrown and a message is seen
     */
    public void readMeButtonPressed() {

        try {
            //DesktopApi.open(new File("README.md"));
            java.awt.Desktop.getDesktop().edit(new File("README.md"));
        } catch(Exception e){
            errorReadingFile.setOpacity(1.00);
        }

    }

    /**
     * allows user to load a new custom spelling list
     */
    public void newSpellingList(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select New Spelling List");

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Text Files", "*.txt")
        );

        File selectedFile = fileChooser.showOpenDialog(Voxspell.getPrimaryStage());

        if (selectedFile != null) {

            _dataBase.importWordList(selectedFile.getAbsolutePath());
            selectedListAlert.setText(selectedFile.getName() + " LIST SELECTED");
            selectedListAlert.setVisible(true);
        }

    }

    /**
     * sets the spelling list to the default NZCER list
     */
    public void defaultList(){

        _dataBase.importWordList(defaultSpellingListPath);
        selectedListAlert.setText("DEFAULT SPELLING LIST SELECTED");
        selectedListAlert.setVisible(true);

    }


    /**
     * is called when the program (VOXSPELL) is started, printing the saved files from the database
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedListAlert.setVisible(false);
        errorReadingFile.setOpacity(0.00); // should be invisible on start up, visible when exception is thrown
        _dataBase.printSavedFIles();
    }
}

