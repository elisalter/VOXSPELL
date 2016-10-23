package llepsxov.application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by zihao123yang on 16/09/16.
 */
public class Spelling_Logic {

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



    public Spelling_Logic(Statistics stats) {

        _dataBase = DataBase.getInstance();
        _revisionData = RevisionQuiz.getInstance();
        _wordList = new ArrayList<String>();
        _stats = stats;

    }


    public void setUpQuiz () {
        _inputFlag = false;
        _repeatFlag = false;


        if (_isNewQuiz == true) {

            _wordList = _dataBase.makeQuizList(Level.getCurrentlevel());
        } else {

            ArrayList<Word> preparationList = _revisionData.levelListForRevise();

            if (preparationList == null) {

                //go to next scene

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


        _numWords = _wordList.size();

        _position = 0;

        _stats.setNumWords(_wordList.size());

    }


    public void spellingQuiz(String input) {


        if (_inputFlag == false) {

            _word = new Word(_wordList.get(_position),Level.getCurrentlevel());

            Festival.callFestival("Please spell the word " + _wordList.get(_position));

            _inputFlag = true;
            return;


        }

        if (_repeatFlag == false) {

            _word = new Word(_wordList.get(_position), Level.getCurrentlevel());
            if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {

                Festival.callFestival("Correct, well done");



                if (!_isNewQuiz) {
                    _revisionData.removeFromLevel(_word);
                }

            } else {

                Festival.callFestival("Incorrect! Please try again. " + _wordList.get(_position));


                _repeatFlag = true;
                return;
            }

        }

        if (_repeatFlag == true) {

            _word = new Word(_wordList.get(_position), Level.getCurrentlevel());
            if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {
                Festival.callFestival("Correct");


                if (!_isNewQuiz) {
                    _revisionData.removeFromLevel(_word);
                }

            } else {
                Festival.callFestival("Incorrect...");

                _revisionData.addToFailed(_word);


            }
        }

        _repeatFlag = false;
        _position++;


        if (_position < _numWords ) {
            _word = new Word(_wordList.get(_position), Level.getCurrentlevel());
            Festival.callFestival("Please spell the word " + _wordList.get(_position));

            return;
        } else {

            if (!_isNewQuiz) {
                revisionComplete();
            }else if(Level.getCurrentlevel() <= 10) {

                if (_stats.levelPassed()) {
                    levelComplete();
                } else {
                    levelFailed();
                }
            }
        }

    }

    public String currentWord() {
        return _word.name();
    }

    public int whichIteration() {
        if (_repeatFlag) {
            return 2;
        } else {
            return 1;
        }
    }

    public boolean spellingCorrect(String input) {
        if (_wordList.get(_position).toLowerCase().trim().equals(input.toLowerCase().trim())) {
            return true;
        } else {
            return false;
        }
    }



    public void levelComplete() {

        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/LevelComplete.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }

    }

    public void levelFailed() {

        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/LevelFailedScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void revisionComplete() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/RevisionComplete.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void addFailedStats() {
        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addFailed();
        } else {
            _word.addFailed();
            _dataBase.addToWordList(_word);
        }
    }

    public void addFaultedStats() {
        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addFaulted();
        } else {
            _word.addFaulted();
            _dataBase.addToWordList(_word);
        }
    }

    public void addMasteredStats() {
        if (_dataBase.wordSeen(_word)) {
            _word = _dataBase.getWordStatsList(_word);
            _word.addMastered();
        } else {
            _word.addMastered();
            _dataBase.addToWordList(_word);
        }
    }

    public int getNumberWords() {
        return _numWords;
    }

}
