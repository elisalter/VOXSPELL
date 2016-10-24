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
 * JavaFX controller class for the spelling quiz scene
 */
public class SpellingQuizController implements Initializable {

    Statistics _stats = new Statistics();
    private SpellingLogic _spellingLogic = new SpellingLogic(_stats);
    private DataBase _dataBase = DataBase.getInstance();
    private Scores _scores = Scores.getInstance();

    @FXML
    private Text _levelAccuracyText, _testAccuracyText, _levelText, _streakScore, _playerScore, highScoreText, highStreakText;

    @FXML
    private ComboBox selectVoice;

    @FXML
    private TextField _inputField;

    @FXML
    private ProgressBar bar;

    // list of avaiable voices
    ObservableList<String> voiceList = FXCollections.observableArrayList("voice_kal_diphone", "voice_akl_nz_jdt_diphone");

    /**
     * clear the input field when clicked
     */
    @FXML
    public void textFieldClicked() {
        _inputField.clear();
    }

    /**
     * checks the users spelling of selected word when the submit button or enter is pressed
     */
    @FXML
    public void submitButtonPressed() {

        String userInput = _inputField.getText();
        int inputPointsValue = userInput.length();

        _inputField.clear(); // clear the textarea

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

    /**
     * repeat the word when the repeat button is pressed
     */
    @FXML
    public void repeatWordPressed() {
        Festival.callFestival(_spellingLogic.currentWord());
    }

    /**
     * changes the voice dynamically
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
     * sets the background colour based on the correctness of the users answer
     *
     * green for correct
     * red for incorrect
     *
     * @param input users input
     */
    public void textFieldChange(String input) {
        if (_spellingLogic.spellingCorrect(input)) {
            _inputField.setStyle("-fx-background-color: #317873;");
        } else {
            _inputField.setStyle("-fx-background-color:  #933D41");
        }
    }

    /**
     * called on startup of the spelling quiz, initializes the voice and sets up the SpellingLogic object
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectVoice.setValue(Festival.voice());
        selectVoice.setItems(voiceList);

        _spellingLogic.setUpQuiz();
        _spellingLogic.spellingQuiz("");


        // test accuracy begins at 100%, level accuracy is calculated
        _levelText.setText("LEVEL " +  Level.getCurrentlevel());
        _testAccuracyText.setText("TEST ACCURACY: 100.0%");
        _levelAccuracyText.setText("LEVEL ACCURACY: " + _stats.calculateLevelAccuracy(Level.getCurrentlevel()) + "%");

        setAccuracyColour("t", 100.00); // set test acurracy colour to default value for score of 100%
        setAccuracyColour("l", _stats.calculateLevelAccuracy(Level.getCurrentlevel())); // set level colour
        _streakScore.setText(""+_scores._streak);
        _playerScore.setText(""+_scores.get_score());

        setHighScoreAndStreak(); // grab the highscore and high streak values

    }

    /**
     * increments the progress bar based on the number of words left in the quiz
     */
    public void incrementProgressBar(){
        double currentProgress = bar.getProgress();
        double amountToIncrement = 1/(double)_spellingLogic.getNumberWords();
        bar.setProgress(currentProgress+amountToIncrement);
    }

    /**
     * sets the accuracy for both the test and the level
     */
    public void setAccuracyText(){

        double tA = _stats.calculateAccurracy();
        double lA = _stats.calculateLevelAccuracy(Level.getCurrentlevel());

        _testAccuracyText.setText("TEST ACCURACY: " +  tA + "%");
        _levelAccuracyText.setText("LEVEL ACCURACY: " + lA + "%");

        setAccuracyColour("t", tA);
        setAccuracyColour("l", lA);
    }

    /**
     * calculated the associated colour of the accuracy
     *
     * greener the colour the higher the accuracy
     * red for low/poor accuracy
     *
     * @param levelOrTest "l" set level accuracy, "t" set test accuracy
     * @param statsOrLevelAccuracy the associated accuracy value (between 0 and 100)
     */
    public void setAccuracyColour(String levelOrTest, double statsOrLevelAccuracy){

        String colRepresentation;

        if (statsOrLevelAccuracy<50.00) {

            colRepresentation="#f04409";

        } else if (statsOrLevelAccuracy<70.00) {

            colRepresentation="#fb8810";

        } else {

            colRepresentation="#37bf4f";

        }

        // set field accordingly
        if(levelOrTest.toLowerCase().equals("l")) {
            _levelAccuracyText.setFill(Color.web(colRepresentation));
        } else {
            _testAccuracyText.setFill(Color.web(colRepresentation));
        }
    }

    /**
     * sets the highScore and highest streak values in top right hand corner of a spelling quiz
     */
    public void setHighScoreAndStreak(){

        highScoreText.setText("HIGH SCORE: " + _scores.get_highScore());
        highStreakText.setText("HIGH STREAK: " + _scores.get_highStreak());

    }

}
