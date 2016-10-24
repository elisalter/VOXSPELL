package llepsxov.application;

import java.io.*;
import java.util.*;

/**
 * Stores data for the revision quiz, revision quiz related logic is presented here
 * singleton class
 */
public class RevisionQuiz {

    private File _failedFile = new File(".failedStats.ser");
    private Map<Integer, ArrayList<Word>> _failedList;

    private static RevisionQuiz instance = null; // default state of null


    /**
     * private constructor, creating the _failedList object
     */
    private RevisionQuiz() {
        _failedList = new HashMap<Integer, ArrayList<Word>>();
    }

    /**
     * Returns the revisionquiz singleton object
     * @return
     */
    public static RevisionQuiz getInstance() {
        if (instance == null) {
            instance = new RevisionQuiz();
        }
        return instance;
    }

    /**
     * loads the failedList as a serializeable object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadFailed() throws IOException, ClassNotFoundException {

        if (!_failedFile.exists()) { // if no serializeable object exists then return
            return;
        }

        FileInputStream fileIn = new FileInputStream(_failedFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);

        // read in the _failedList object from storage
        _failedList = (Map<Integer, ArrayList<Word>>) objectIn.readObject();

        fileIn.close();
        objectIn.close();
    }

    /**
     * saves the failed file as a serializeable object
     * @throws IOException
     */
    public void saveFailed() throws IOException  {

        // write the _failedFile object out to disk
        FileOutputStream fileOut = new FileOutputStream(_failedFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

        objectOut.writeObject(_failedList);

        objectOut.close();
        fileOut.close();
    }


    /**
     * adds a word to the failed list
     * @param word
     */
    public void addToFailed(Word word) {

        if (_failedList.containsKey(Level.getCurrentlevel())) {//if failed list has words from current level

            ArrayList<Word> failedLevelList = _failedList.get(Level.getCurrentlevel()); // get those words

            if (!failedLevelList.contains(word)) {
                failedLevelList.add(word); // if word hasn't already been added, add it
            }

        } else {

            ArrayList<Word> failedLevelList = new ArrayList<Word>(); // make new failedLevelList
            failedLevelList.add(word); // store the word in it
            _failedList.put(Level.getCurrentlevel(), failedLevelList); // put it in the _failedList arraylist
        }
    }


    /**
     * returns the list to revise for a review quiz
     * @return list of words to revise
     */
    public ArrayList<Word> levelListForRevise() {

        if (_failedList.containsKey(Level.getCurrentlevel())) {

            ArrayList failedLevel = _failedList.get(Level.getCurrentlevel());

            // randomise the words for the test
            long seed = System.nanoTime();
            Collections.shuffle(failedLevel, new Random(seed));

            return failedLevel;

        } else {
            return null;// no words failed for current level
        }
    }

    /**
     * removes a word from the review quiz mode
     * @param word = word to remove
     */
    public void removeFromLevel(Word word) {

        ArrayList<Word> levelList = _failedList.get(Level.getCurrentlevel());

        if (levelList.contains(word)) {
            levelList.remove(word);
        }
    }


    /**
     * checks if any words failed for a level
     * @param level = level to check
     * @return true if there are words
     */
    public boolean checkAnyWords(int level) {

        if (_failedList.containsKey(level)) { // if words are failed from current level

            if(_failedList.get(level).size() > 0) { // if size greater than zero
                return true; // yes there are words
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * clears the failed list of words
     */
    public void clearFailed() {
        _failedList = new HashMap<Integer,ArrayList<Word>>();
    }

}
