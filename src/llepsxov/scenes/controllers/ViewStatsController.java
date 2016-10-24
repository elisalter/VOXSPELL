package llepsxov.scenes.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import llepsxov.application.Voxspell;
import llepsxov.application.Word;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import llepsxov.application.*;

/**
 * JavaFX controller class for the statistics screen in VOXSPELL
 */
public class ViewStatsController implements Initializable {

    @FXML
    Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10; // level buttons

    @FXML
    TableColumn wordCol, appearedCol, masteredCol, faultedCol, failedCol; // table colums

    @FXML
    TableView statsTable;

    DataBase _db = DataBase.getInstance();

    ArrayList<Button> levelButtons = new ArrayList<Button>();

    /**
     * called on the start up of the statistics screen, sets level 1 as the default stats display
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButtons();
        setDataPropertiesToColumns();
        for(Button b : levelButtons){b.setOpacity(0.5);}
        level1Pressed(); // initialize table to level one

    }

    /**
     * adds all buttons to arraylist
     */
    public void addButtons(){
        levelButtons.addAll(Arrays.asList(Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10));
    }

    //==================================================================================================================
    // Below are methods for when level buttons are pressed
    // the tables data update method is called with the associated level input
    // transparency of other buttons is changed to highlight the level being displayed


    @FXML
    public void level1Pressed(){
        populateObservableList(1);
        buttonClicked(Button1);

    }

    @FXML
    public void level2Pressed(){
        populateObservableList(2);
        buttonClicked(Button2);
    }

    @FXML
    public void level3Pressed() {
        populateObservableList(3);
        buttonClicked(Button3);
    }

    @FXML
    public void level4Pressed(){
        populateObservableList(4);
        buttonClicked(Button4);

    }

    @FXML
    public void level5Pressed(){
        populateObservableList(5);
        buttonClicked(Button5);

    }

    @FXML
    public void level6Pressed(){
        populateObservableList(6);
        buttonClicked(Button6);

    }

    @FXML
    public void level7Pressed(){
        populateObservableList(7);
        buttonClicked(Button7);

    }

    @FXML
    public void level8Pressed(){
        populateObservableList(8);
        buttonClicked(Button8);

    }

    @FXML
    public void level9Pressed(){
        populateObservableList(9);
        buttonClicked(Button9);

    }

    @FXML
    public void level10Pressed(){
        populateObservableList(10);
        buttonClicked(Button10);

    }


    //=================================================================================================================

    /**
     * switches scene to main menu
     * @throws IOException
     */
    @FXML
    public void returnToMainMenu() throws IOException{
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * dims buttons not clicked and highlights button that was clicked
     * @param btnClicked
     */
    public void buttonClicked(Button btnClicked){
        for(Button b : levelButtons){
            if(b.equals(btnClicked)){
                b.setOpacity(1.0);
            } else {
                b.setOpacity(0.5);
            }
        }
    }

    /**
     * updates the StatsTable to display stats for the selected level
     * @param level
     */
    public void populateObservableList(int level){

        ObservableList<Word> data = FXCollections.observableArrayList(); // reinitialises the data model

        //for every word int he data base
        for(Word word : _db.getStoredStats()){
            if (word.getLevel() == level) { // if word is in right level, add it
                data.add(word);

            }
        }

        wordCol.setSortType(TableColumn.SortType.DESCENDING);

        // add new data set to the table
        statsTable.setItems(data);

    }

    /**
     * sets the data properties for the colums of the statistics table based on fields in the llepsxov.application.word
     * class
     */
    public void setDataPropertiesToColumns(){

        wordCol.setCellValueFactory(
                new PropertyValueFactory<Word,String>("_word")
        );

        appearedCol.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("NumAppeared")
        );

        masteredCol.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("NumMastered")
        );

        faultedCol.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("NumFaulted")
        );

        failedCol.setCellValueFactory(
                new PropertyValueFactory<Word, Integer>("NumFailed")
        );
    }

}
