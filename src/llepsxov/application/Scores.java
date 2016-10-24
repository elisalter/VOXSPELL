package llepsxov.application;

import java.io.*;

/**
 * Serializable object, used to represent information about streaks and scores
 *
 * also a singleton class
 *
 * streak = number of words correct in a row (mastered)
 * score = increments based on number of characters in word, if the word is faulted then half points apply, if the word
 *          is failed then points is set to zero.
 */
public class Scores implements Serializable{

    public int _streak;
    public int _score;
    public int _highScore;
    public int _highStreak;

    public File file = new File(".highScores.ser"); // default file to write scores to

    private static Scores instance = null; // singleton instance


    /**
     * loads .highScores.ser serializable object and sets scores+streaks gamestate
     */
    public void loadScores() {

        if(file.exists()) { // only load the object if the file exists
            try {

                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                Scores oldScores = (Scores)objectIn.readObject();

                // set up state from old object
                _streak = oldScores._streak;
                _score = oldScores._score;
                _highScore = oldScores._highScore;
                _highStreak = oldScores._highStreak;

                fileIn.close();
                objectIn.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * returns Scores instance
     * @return
     */
    public static Scores getInstance(){
        if(instance == null){
            instance = new Scores();
        }
        return instance;
    }

    //=================================================================================================================
    // Getters and setters follow
    //=================================================================================================================

    public int get_streak() {
        return _streak;
    }

    public void set_streak(int _streak) {
        this._streak = _streak;
    }

    public int get_score() {
        return _score;
    }

    public void set_score(int _score) {
        this._score = _score;
    }

    public int get_highScore() {
        return _highScore;
    }

    public void set_highScore(int _highScore) {
        this._highScore = _highScore;
    }

    public int get_highStreak() {
        return _highStreak;
    }

    public void set_highStreak(int _highStreak) {
        this._highStreak = _highStreak;
    }

    /**
     * save game state to .highScores.ser
     */
    public void saveData() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.close();


        } catch (IOException e) {
            System.out.println("Exception thrown in Scores#saveData()"); //should use logger
        }
    }

    /**
     * resets _streak, _score, _highScore, and _highStreak to zero.
     */
    public void clearScores(){
        _streak = 0;
        _score = 0;
        _highScore = 0;
        _highStreak = 0;
    }

}
