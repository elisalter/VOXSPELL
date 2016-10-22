package llepsxov.scenes.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import llepsxov.application.DisplayingStats;
import llepsxov.application.Voxspell;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by eli on 22/09/16.
 */
public class ViewStatsController implements Initializable {

    @FXML
    TextArea statsDisplay;

    @FXML
    Button Button1;

    @FXML
    Button Button2;

    @FXML
    Button Button3;

    @FXML
    Button Button4;

    @FXML
    Button Button5;

    @FXML
    Button Button6;

    @FXML
    Button Button7;

    @FXML
    Button Button8;

    @FXML
    Button Button9;

    @FXML
    Button Button10;



    ArrayList<Button> levelButtons = new ArrayList<Button>();

    DisplayingStats _statistics = new DisplayingStats();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        addButtons();
        statsDisplay.setText("Select A level");
        for(Button b : levelButtons){b.setOpacity(0.5);}

    }

    public void addButtons(){
        levelButtons.addAll(Arrays.asList(Button1, Button2, Button3, Button4, Button5, Button6, Button7, Button8, Button9, Button10));
    }

    @FXML
    public void level1Pressed(){
        buttonClicked(Button1);
        String outputText = _statistics.printStatistics(1);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 1");
        } else {
            statsDisplay.setText(_statistics.printStatistics(1));
        }
    }

    @FXML
    public void level2Pressed(){
        buttonClicked(Button2);
        String outputText = _statistics.printStatistics(2);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 2");
        } else {
            statsDisplay.setText(_statistics.printStatistics(2));
        }
    }

    @FXML
    public void level3Pressed(){
        buttonClicked(Button3);
        String outputText = _statistics.printStatistics(3);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 3");
        } else {
            statsDisplay.setText(_statistics.printStatistics(3));
        }
    }

    @FXML
    public void level4Pressed(){
        buttonClicked(Button4);
        String outputText = _statistics.printStatistics(4);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 4");
        } else {
            statsDisplay.setText(_statistics.printStatistics(4));
        }
    }

    @FXML
    public void level5Pressed(){
        buttonClicked(Button5);
        String outputText = _statistics.printStatistics(5);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 5");
        } else {
            statsDisplay.setText(_statistics.printStatistics(5));
        }
    }

    @FXML
    public void level6Pressed(){
        buttonClicked(Button6);
        String outputText = _statistics.printStatistics(6);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 6");
        } else {
            statsDisplay.setText(_statistics.printStatistics(6));
        }
    }

    @FXML
    public void level7Pressed(){
        buttonClicked(Button7);
        String outputText = _statistics.printStatistics(7);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 7");
        } else {
            statsDisplay.setText(_statistics.printStatistics(7));
        }
    }

    @FXML
    public void level8Pressed(){
        buttonClicked(Button8);
        String outputText = _statistics.printStatistics(8);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 8");
        } else {
            statsDisplay.setText(_statistics.printStatistics(8));
        }
    }

    @FXML
    public void level9Pressed(){
        buttonClicked(Button9);
        String outputText = _statistics.printStatistics(9);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 9");
        } else {
            statsDisplay.setText(_statistics.printStatistics(9));
        }
    }

    @FXML
    public void level10Pressed(){
        buttonClicked(Button10);
        String outputText = _statistics.printStatistics(10);
        if(outputText.equals("")){
            statsDisplay.setText("No stats to display for level 10");
        } else {
            statsDisplay.setText(_statistics.printStatistics(10));
        }
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
}
