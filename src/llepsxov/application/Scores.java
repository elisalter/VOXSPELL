package llepsxov.application;

import java.io.*;

/**
 * Created by eli on 23/10/16.
 */
public class Scores implements Serializable{

    public int _streak = 0;
    public int _score = 0;
    public int _highScore=0;
    public int _highStreak=0;
    public File file = new File(".highScores.ser");
    private static Scores instance = null;


    public void loadScores() {
        if(file.exists()) {
            try {

                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                Scores oldScores = (Scores)objectIn.readObject();

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
        } else {
            // initialise fields if users first play through
            //_streak = 0;
            //_score = 0;
            //_highScore = 0;
            //_highStreak = 0;

        }
    }

    public static Scores getInstance(){
        if(instance == null){
            instance = new Scores();
        }
        return instance;
    }

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

    public void saveData() {
        try {

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(this);
            oos.close();


        } catch (IOException e) {

        }
    }

    public void clearScores(){
        _streak = 0;
        _score = 0;
        _highScore=0;
        _highStreak=0;
    }
}
