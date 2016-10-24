package llepsxov.scenes.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import llepsxov.application.*;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 19/09/16.
 */
public class SpellingQuizController implements Initializable {

    Statistics _stats = new Statistics();
    private SpellingLogic _spellingLogic = new SpellingLogic(_stats);
    private DataBase _dataBase = DataBase.getInstance();
    private Scores _scores = Scores.getInstance();

    @FXML
    private Text _levelAccuracyText;

    @FXML
    private Text _testAccuracyText;

    @FXML
    private Text _levelText;

    @FXML
    private ComboBox selectVoice;

    @FXML
    private TextField _inputField;

    @FXML
    private Button _submitButton;

    @FXML
    private ProgressBar bar;

    @FXML
    private Text _streakScore; // displays current streak value

    @FXML
    private Text _playerScore; // displays current score value

    @FXML
    private Text highScoreText;

    @FXML
    private Text highStreakText;

    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    @FXML
    public void textFieldClicked() {
        _inputField.clear();
    }

    @FXML
    public void submitButtonPressed() {

        String userInput = _inputField.getText();
        int inputPointsValue = userInput.length();

        _inputField.clear();

        textFieldChange(userInput);
        int iteration = _spellingLogic.whichIteration();

        if(iteration == 1) {        // mastered, ...

            if (_spellingLogic.spellingCorrect(userInput)) {

                _scores.set_streak(_scores.get_streak()+1);//increment current streak by 1

                _scores.set_score(_scores.get_score()+inputPointsValue);
                _streakScore.setText(""+_scores.get_streak());
                _playerScore.setText(""+_scores.get_score());

                _stats.increaseMastered();
                _spellingLogic.addMasteredStats();

                setAccuracyText();
                incrementProgressBar();
                setHighScoreAndStreak();

            } else { // did not master the word, set the streak back to zero

                //check if new high streak!
                if(_scores.get_streak() > _scores.get_highStreak()){ // if streak that just eneded > highstreak

                    _scores.set_highStreak(_scores.get_streak()); // set streak as streak that's just ended

                }
                setHighScoreAndStreak();

                _scores.set_streak(0); // reset streak to zero
                _streakScore.setText(""+_scores._streak); // set the streak text back to zero too
            }

        } else if (iteration == 2) {    //faulted, failed


            if (_spellingLogic.spellingCorrect(userInput)) {// word is correct on second attempt, word was faulted

                _scores.set_score(_scores.get_score()+Math.round(userInput.length()/2)); // gets half amount of points as faulted
                _playerScore.setText(""+_scores.get_score());

                _stats.increaseFaulted();
                _spellingLogic.addFaultedStats();

                incrementProgressBar();
                setAccuracyText();
                setHighScoreAndStreak();


            } else { // failed the word, set score back to zero

                //check if score is higher than old highscore
                if(_scores.get_score() > _scores.get_highScore()){

                    _scores.set_highScore(_scores.get_score());

                }
                _scores.set_score(0);
                _playerScore.setText(""+_scores.get_score());

                incrementProgressBar();
                _stats.increaseFailed();
                _spellingLogic.addFailedStats();
                setHighScoreAndStreak();

            }
            setAccuracyText();
            setHighScoreAndStreak();
        }

        _spellingLogic.spellingQuiz(userInput);

    }

    @FXML
    public void repeatWordPressed() {
        Festival.callFestival(_spellingLogic.currentWord());
    }

    @FXML
    public void voiceChanging() {
        if (selectVoice.getValue().equals("voice_kal_diphone")) {
            Festival.setVoice("voice_kal_diphone");
        } else if (selectVoice.getValue().equals("voice_akl_nz_jdt_diphone")) {
            Festival.setVoice("voice_akl_nz_jdt_diphone");
        }
    }

    public void textFieldChange(String input) {
        if (_spellingLogic.spellingCorrect(input)) {
            _inputField.setStyle("-fx-background-color: #317873;");
        } else {
            _inputField.setStyle("-fx-background-color:  #933D41");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);

        _spellingLogic.setUpQuiz();
        _spellingLogic.spellingQuiz("");

        _levelText.setText("LEVEL " +  Level.getCurrentlevel());
        _testAccuracyText.setText("TEST ACCURACY: 100.0%");
        _levelAccuracyText.setText("LEVEL ACCURACY: " + _stats.calculateLevelAccuracy(Level.getCurrentlevel()) + "%");

        setAccuracyColour("t", 100.00); // set test acurracy colour to default value for score of 100%
        setAccuracyColour("l", _stats.calculateLevelAccuracy(Level.getCurrentlevel())); // set level colour
        _streakScore.setText(""+_scores._streak);
        _playerScore.setText(""+_scores.get_score());

        setHighScoreAndStreak();

    }

    public void incrementProgressBar(){
        double currentProgress = bar.getProgress();
        double amountToIncrement = 1/(double)_spellingLogic.getNumberWords();
        bar.setProgress(currentProgress+amountToIncrement);
    }

    public void setAccuracyText(){

        double tA = _stats.calculateAccurracy();
        double lA = _stats.calculateLevelAccuracy(Level.getCurrentlevel());

        _testAccuracyText.setText("TEST ACCURACY: " +  tA + "%");
        _levelAccuracyText.setText("LEVEL ACCURACY: " + lA + "%");

        setAccuracyColour("t", tA);
        setAccuracyColour("l", lA);


    }

    public void setAccuracyColour(String levelOrTest, double statsOrLevelAccuracy){

        String colRepresentation;


        if (statsOrLevelAccuracy<50.00) {

            colRepresentation="#f04409";

        } else if (statsOrLevelAccuracy<70.00) {

            colRepresentation="#fb8810";

        } else {

            colRepresentation="#37bf4f";

        }

        if(levelOrTest.toLowerCase().equals("l")) {
            _levelAccuracyText.setFill(Color.web(colRepresentation));
        } else {
            _testAccuracyText.setFill(Color.web(colRepresentation));
        }
    }

    public void setHighScoreAndStreak(){

        highScoreText.setText("HIGH SCORE: " + _scores.get_highScore());
        highStreakText.setText("HIGH STREAK: " + _scores.get_highStreak());

    }

}
