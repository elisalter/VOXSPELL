package llepsxov.application;

/**
 * Created by zihao123yang on 16/09/16.
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * representation of the database for our program, stores words from text file, and also the statistics for words seen in the spelling quiz
 */
public class DataBase {

    private static DataBase instance = null;

    private File _statsFile = new File(".stats.ser");
    private File _failedFile = new File(".failedStats.ser");
    private ArrayList<Word> _storedStats;
    private ArrayList<Word> _failedList;
    //private ArrayList<String> _wordList;

    private Map<Integer, ArrayList<String>> _wordList;


    private DataBase() {
        _storedStats = new ArrayList<Word>();
        _failedList = new ArrayList<Word>();
        //_wordList = new ArrayList<String>();

        if (_wordList == null) {
            _wordList = new HashMap<Integer, ArrayList<String>>();
            importWordList("./src/llepsxov/spelling/NZCER-spelling-lists.txt"); //loads default list on construction
        }
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }

        return instance;
    }


    /**
     * saves statistics when program closes
     * @throws IOException
     */
    public void saveStats() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(_statsFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(_storedStats);
        fileOut.close();
        objectOut.close();
        /*
        fileOut = new FileOutputStream(_failedFile);
        objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(_failedList);
        objectOut.close();
        fileOut.close();
        */
    }


    /**
     * load stats when program opens
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadStats() throws IOException, ClassNotFoundException {
        if (!_statsFile.exists()) {
            return;
        }
        FileInputStream fileIn = new FileInputStream(_statsFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        _storedStats = (ArrayList<Word>) objectIn.readObject();
        fileIn.close();
        objectIn.close();
    }


    public void loadFailed() throws IOException, ClassNotFoundException {
        if (!_failedFile.exists()) {
            return;
        }
        FileInputStream fileIn = new FileInputStream(_failedFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        _failedList = (ArrayList<Word>) objectIn.readObject();
        fileIn.close();
        objectIn.close();
    }


    /**
     * imports words from file and puts into hashmap with levels as keys
     */
    public void importWordList(String filePath) {

        String currentLine;
        int level = 1;
        ArrayList<String> levelList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));

            while ((currentLine = br.readLine()) != null) {
                if (currentLine.charAt(0) == '%') {

                    levelList = new ArrayList<String>();
                    if (level < 11 && level > 0) {
                        _wordList.put(level, levelList);
                    }
                    level++;
                } else {
                    levelList.add(currentLine);
                }
            }

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * makes list of words for quiz
     * @param level
     * @return
     */
    public ArrayList<String> makeQuizList(int level) {
//
        ArrayList<String> levelList = _wordList.get(level);
        int size = levelList.size();
        randomizeList(levelList);

        if (level > 10) {
            return null;
        } else if (size >= 10) {
            return new ArrayList<String> (levelList.subList(0, 10));

        } else {
            return levelList;
        }

        // debug statements
        // levelList debug


    }


    public void sortList() {
        Collections.sort(_storedStats);

    }


    /**
     * clears stats file
     */
    public void clearStats() {
        _storedStats = new ArrayList<Word>();
        _failedList = new ArrayList<Word>();
    }


    public void addToWordList(Word word) {

        _storedStats.add(word);
    }


    public void addToFailedList(Word word) {
        if (!_failedList.contains(word)) {
            _failedList.add(word);
        }
    }


    public void removeFromFailedList(Word word) {
        if (_failedList.contains(word)) {
            _failedList.remove(word);
        }
    }


    public void randomizeList(ArrayList<String> list) {

        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
    }


    public void randomizefailedList() {

        long seed = System.nanoTime();
        Collections.shuffle(_failedList, new Random(seed));
    }


    public int sizeOfFailed() {

        return _failedList.size();
    }


    public int sizeOfStats() {

        return _storedStats.size();
    }

    public void printSavedFIles() {


        for (int i = 0; i < _storedStats.size(); i++) {

        }



        for (int i =0; i < _failedList.size(); i++) {

        }

    }

    /**
     * checks if word has already previously appeared in quiz
     * @param word
     * @return
     */
    public boolean wordSeen(Word word) {
        if (_storedStats.contains(word)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get the list of words for a particular level for stats
     * @param word
     * @return
     */
    public Word getWordStatsList(Word word) {


        if (_storedStats.contains(word)) {
            int index = _storedStats.indexOf(word);
            return _storedStats.get(index);
        } else {
            return word;
        }
    }

    public ArrayList<Word> getStoredStats(){
        return _storedStats;
    }

}
