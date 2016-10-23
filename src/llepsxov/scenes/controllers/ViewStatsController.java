package llepsxov.scenes.controllers;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import llepsxov.application.DisplayingStats;
import llepsxov.application.Voxspell;
import llepsxov.application.Word;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import llepsxov.application.*;

/**
 * Created by eli on 22/09/16.
 */
public class ViewStatsController implements Initializable {

    @FXML
    Button Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10;

    @FXML
    TableColumn wordCol, appearedCol, masteredCol, faultedCol, failedCol;

    @FXML
    TableView statsTable;

    DataBase _db = DataBase.getInstance();

    ArrayList<Button> levelButtons = new ArrayList<Button>();

    DisplayingStats _statistics = new DisplayingStats();

    ObservableList<Word> data = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addButtons();
        setDataPropertiesToColumns();
        for(Button b : levelButtons){b.setOpacity(0.5);}
        level1Pressed(); // initialize table to level one

    }

    public void addButtons(){
        levelButtons.addAll(Arrays.asList(Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10));
    }

    @FXML
    public void level1Pressed(){
        populateObservableList(1);
        buttonClicked(Button1);
        String outputText = _statistics.printStatistics(1);

    }

    @FXML
    public void level2Pressed(){
        populateObservableList(2);
        buttonClicked(Button2);
        String outputText = _statistics.printStatistics(2);
    }

    @FXML
    public void level3Pressed() {
        populateObservableList(3);
        buttonClicked(Button3);
        String outputText = _statistics.printStatistics(3);
    }

    @FXML
    public void level4Pressed(){
        populateObservableList(4);
        buttonClicked(Button4);
        String outputText = _statistics.printStatistics(4);

    }

    @FXML
    public void level5Pressed(){
        populateObservableList(5);
        buttonClicked(Button5);
        String outputText = _statistics.printStatistics(5);

    }

    @FXML
    public void level6Pressed(){
        populateObservableList(6);
        buttonClicked(Button6);
        String outputText = _statistics.printStatistics(6);

    }

    @FXML
    public void level7Pressed(){
        populateObservableList(7);
        buttonClicked(Button7);
        String outputText = _statistics.printStatistics(7);

    }

    @FXML
    public void level8Pressed(){
        populateObservableList(8);
        buttonClicked(Button8);
        String outputText = _statistics.printStatistics(8);

    }

    @FXML
    public void level9Pressed(){
        populateObservableList(9);
        buttonClicked(Button9);
        String outputText = _statistics.printStatistics(9);

    }

    @FXML
    public void level10Pressed(){
        populateObservableList(10);
        buttonClicked(Button10);
        String outputText = _statistics.printStatistics(10);

    }

    @FXML
    public void returnToMainMenu() throws IOException{
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/MainMenuScene.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void buttonClicked(Button btnClicked){
        for(Button b : levelButtons){
            if(b.equals(btnClicked)){
                b.setOpacity(1.0);
            } else {
                b.setOpacity(0.5);
            }
        }
    }

    public void populateObservableList(int level){

        data = FXCollections.observableArrayList();

        for(Word word : _db.getStoredStats()){

            if (word.getLevel() == level) {
                data.add(word);

            }
        }

        wordCol.setSortType(TableColumn.SortType.DESCENDING);
        statsTable.setItems(data);

    }

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
