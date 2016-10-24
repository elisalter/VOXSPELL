package llepsxov.application;

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
 * Singleton DataBase class, holds lists of all word objects seeb by current user, can store words from
 * multiple wordlists and is only cleared when statistics are cleared.
 */
public class DataBase {

    private static DataBase instance = null;

    private File _statsFile = new File(".stats.ser");
    private File _failedFile = new File(".failedStats.ser");

    private ArrayList<Word> _storedStats;
    private ArrayList<Word> _failedList;


    private Map<Integer, ArrayList<String>> _wordList;


    /**
     * DataBase object constructor, loads the default NZCER-spelling-list when constructed is called by the
     * DataBase#getInstance method if the object has not been instantiated
     */
    private DataBase() {
        _storedStats = new ArrayList<Word>();
        _failedList = new ArrayList<Word>();


        if (_wordList == null) {
            _wordList = new HashMap<Integer, ArrayList<String>>();
            importWordList("./src/llepsxov/spelling/NZCER-spelling-lists.txt"); //loads default list on construction
        }
    }

    /**
     * if the DataBase instance is null, construct the object, if already constructed return it.
     * @return the DataBase instance
     */
    public static DataBase getInstance() {

        if (instance == null) {
            instance = new DataBase();
        }

        return instance;
    }


    /**
     * Saves user statistics as a serializable object when program closes
     * @throws IOException
     */
    public void saveStats() throws IOException {

        // write the _storedStats object to disk
        FileOutputStream fileOut = new FileOutputStream(_statsFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(_storedStats);

        fileOut.close();
        objectOut.close();
    }


    /**
     * Load serialized statistics object when program is opened
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadStats() throws IOException, ClassNotFoundException {

        // if there is no previous stats, then don't load anything
        if (!_statsFile.exists()) {
            return;
        }

        // load the object
        FileInputStream fileIn = new FileInputStream(_statsFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        //initialse the stats object
        _storedStats = (ArrayList<Word>) objectIn.readObject();

        // close the file
        fileIn.close();
        objectIn.close();
    }


    /**
     * Load serialized object representing failed words when the program is opened
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadFailed() throws IOException, ClassNotFoundException {

        // if there is no previous failed file, return
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
     * Imports words from file and puts into hashmap with levels as keys and word as value
     * @param filePath, string representation of wordlist filepath
     */
    public void importWordList(String filePath) {

        String currentLine;
        int level = 1; // read from level 1

        ArrayList<String> levelList = new ArrayList<>();

        try {

            BufferedReader br = new BufferedReader(new FileReader(filePath));

            // parse the input
            while ((currentLine = br.readLine()) != null) {

                if (currentLine.charAt(0) == '%') {

                    levelList = new ArrayList<String>();

                    if (level < 11 && level > 0) {// if invalid level
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
     * @return ArrayList<String> of words for the test
     */
    public ArrayList<String> makeQuizList(int level) {

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
    }


    /**
     * sorts the _storedStats object
     */
    public void sortList() {
        Collections.sort(_storedStats);
    }


    /**
     * clears stats file (_storedStats and _failedList)
     */
    public void clearStats() {
        _storedStats = new ArrayList<Word>();
        _failedList = new ArrayList<Word>();
    }


    /**
     * adds a word to the word list
     * @param word, the word to add
     */
    public void addToWordList(Word word) {
        _storedStats.add(word);
    }


    /**
     * adds a word to the failed list
     * @param word = word to add to failed list
     */
    public void addToFailedList(Word word) {
        //only add the if the failed list doesn't already contain the word
        if (!_failedList.contains(word)) {
            _failedList.add(word);
        }
    }


    /**
     * remove a word from the failed list
     * @param word
     */
    public void removeFromFailedList(Word word) {
        //if the failed list contains the word, attempt to remove it
        if (_failedList.contains(word)) {
            _failedList.remove(word);
        }
    }


    /**
     * shuffle the list of words for the test
     * @param list = list of words to shuffle
     */
    public void randomizeList(ArrayList<String> list) {
        long seed = System.nanoTime();
        Collections.shuffle(list, new Random(seed));
    }


    /**
     * shuffle the list of words for the failed list (review quiz)
     */
    public void randomizefailedList() {
        long seed = System.nanoTime();
        Collections.shuffle(_failedList, new Random(seed));
    }

    /**
     * returns the size of the failed list
     * @return length of failed list
     */
    public int sizeOfFailed() {
        return _failedList.size();
    }


    /**
     * gets the size of the stats object
     * @return size of stored stats
     */
    public int sizeOfStats() {
        return _storedStats.size();
    }


    /**
     * checks if word has already previously appeared in quiz
     * @param word
     * @return boolean, has the word appeared
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
     * @return Word from stats list
     */
    public Word getWordStatsList(Word word) {
        if (_storedStats.contains(word)) {
            int index = _storedStats.indexOf(word);
            return _storedStats.get(index);
        } else {
            return word;
        }
    }

    /**
     * gets the stored stats object
     * @return returns storedStats object
     */
    public ArrayList<Word> getStoredStats(){
        return _storedStats;
    }

}
