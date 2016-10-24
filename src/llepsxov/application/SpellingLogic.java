package llepsxov.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Class used for implementing the Logic of a spelling quiz in VOXSPELL
 */
public class SpellingLogic {

    private DataBase _dataBase;
    private ArrayList<String> _wordList;
    private Statistics _stats;
    private RevisionQuiz _revisionData;

    public static boolean _isNewQuiz;
    private boolean _inputFlag;
    private boolean _repeatFlag;
    private int _position;
    private int _numWords;
    private Word _word;


    /**
     * constructor takes in the statistics object and initializes data
     * @param stats
     */
    public SpellingLogic(Statistics stats) {

        _dataBase = DataBase.getInstance();
        _revisionData = RevisionQuiz.getInstance();
        _wordList = new ArrayList<String>();
        _stats = stats;

    }


    /**
     * sets up a spelling quiz
     */
    public void setUpQuiz () {

        _inputFlag = false;
        _repeatFlag = false;


        if (_isNewQuiz == true) { // if new quiz

            _wordList = _dataBase.makeQuizList(Level.getCurrentlevel());

        } else { // it's a revision quiz

            // initialises the preparationList
            ArrayList<Word> preparationList = _revisionData.levelListForRevise();

            // if no words in the revisionQuiz
            if (preparationList == null) {

                //go to next scene


            // fills in preparationList
            } else if (preparationList.size() < 10) {

                for (int i = 0; i < preparationList.size(); i++) {
                    _wordList.add(preparationList.get(i).name());
                }

            } else if (preparationList.size() >= 10) {

                for (int i = 0; i < 10; i++) {
                    _wordList.add(preparationList.get(i).name());
                }
            }

        }


        // update fields
        _numWords = _wordList.size();

        _position = 0;

        _stats.setNumWords(_wordList.size());

    }


    /**
     * runs a spelling quiz for a given word
     * @param input = word to test
     */
    public void spellingQuiz(String input) {

        if (_inputFlag == false) { // if we need to call a word

            _word = new Word(_wordList.get(_position),Level.getCurrentlevel()); // get the word

            Festival.callFestival("Please spell the word " + _wordList.get(_position)); // call it

            _inputFlag = true; // set the input flag (waiting for user input)

            return;


        }

        if (_repeatFlag == false) {

            // get the word
            _word = new Word(_wordList.get(_position), Level.getCurrentlevel());

            // if the users word is correct
            if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {

                Festival.callFestival("Correct, well done");

                // if it's a review quiz and we've mastered
                if (!_isNewQuiz) {
                    _revisionData.removeFromLevel(_word); // remove the word from the quiz
                }

            } else { // word incorrect on first attempt

                Festival.callFestival("Incorrect! Please try again. " + _wordList.get(_position)); // repeat the word

                _repeatFlag = true; // set flag

                return;
            }

        }

        if (_repeatFlag == true) { // word already incorrect once

            _word = new Word(_wordList.get(_position), Level.getCurrentlevel()); // get the word

            if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) { // faulted the world

                Festival.callFestival("Correct");

                // if it's a review quiz
                if (!_isNewQuiz) {
                    _revisionData.removeFromLevel(_word); // remove the word
                }

            } else {

                Festival.callFestival("Incorrect..."); // failed the word
                _revisionData.addToFailed(_word); // add to failed list

            }
        }

        // reset the flag
        _repeatFlag = false;

        // next word
        _position++;


        // if quiz still running
        if (_position < _numWords ) {

            // get the new word
            _word = new Word(_wordList.get(_position), Level.getCurrentlevel());
            Festival.callFestival("Please spell the word " + _wordList.get(_position));

            return;


        } else { // quiz complete

            if (!_isNewQuiz) {
                revisionComplete(); // revision quiz complete

            }else if(Level.getCurrentlevel() <= 10) { // "new quiz" complete (not review)

                if (_stats.levelPassed()) {
                    levelComplete();
                } else {
                    levelFailed();
                }

            }
        }
    }

    /**
     * return the current word the application is dealing with
     * @return name of word
     */
    public String currentWord() {
        return _word.name();
    }

    /**
     * returns the iteration number
     * @return iteration number
     */
    public int whichIteration() {
        if (_repeatFlag) {
            return 2;
        } else {
            return 1;
        }
    }

    /**
     * checks the spelling of the users word
     * @param input = word to test
     * @return boolean is the thing correct
     */
    public boolean spellingCorrect(String input) {

        if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * level has been completed
     */
    public void levelComplete() {

        // load the levelCompleted scene
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/LevelComplete.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }

    }

    /**
     * level has been failed, load new LevelFailedScene.fxml
     */
    public void levelFailed() {

        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/LevelFailedScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * revision quiz has been completed, load RevisionComplete.fxml
     */
    public void revisionComplete() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/RevisionComplete.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * add current word failedStats list
     *
     * (word incorrect on both iterations)
     */
    public void addFailedStats() {

        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addFailed();
        } else {
            _word.addFailed();
            _dataBase.addToWordList(_word);
        }

    }

    /**
     * add current word to Faulted list
     *
     * (word incorrect on first attempt, correct on second)
     */
    public void addFaultedStats() {
        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addFaulted();
        } else {
            _word.addFaulted();
            _dataBase.addToWordList(_word);
        }
    }

    /**
     * add word to mastered list
     *
     * (correct first attempt)
     */
    public void addMasteredStats() {
        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addMastered();
        } else {
            _word.addMastered();
            _dataBase.addToWordList(_word);
        }
    }

    /**
     * return the number of words
     * @return int, number of words
     */
    public int getNumberWords() {
        return _numWords;
    }

}
