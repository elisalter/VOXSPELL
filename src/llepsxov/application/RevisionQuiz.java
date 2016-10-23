package llepsxov.application;



import java.io.*;
import java.util.*;

/**
 * Stores data for the revision quiz, revision quiz related logic is presented here
 * Created by zihao123yang on 22/09/16.
 */
public class RevisionQuiz {



    private File _failedFile = new File(".failedStats.ser");
    private Map<Integer, ArrayList<Word>> _failedList;

    private static RevisionQuiz instance = null;


    private RevisionQuiz() {

        _failedList = new HashMap<Integer, ArrayList<Word>>();

    }

    public static RevisionQuiz getInstance() {
        if (instance == null) {
            instance = new RevisionQuiz();
        }

        return instance;
    }





    public void loadFailed() throws IOException, ClassNotFoundException {
        if (!_failedFile.exists()) {
            return;
        }
        FileInputStream fileIn = new FileInputStream(_failedFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        _failedList = (Map<Integer, ArrayList<Word>>) objectIn.readObject();
        fileIn.close();
        objectIn.close();
    }

    public void saveFailed() throws IOException  {
        FileOutputStream fileOut = new FileOutputStream(_failedFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(_failedList);
        objectOut.close();
        fileOut.close();
    }


    public void addToFailed(Word word) {

        if (_failedList.containsKey(Level.getCurrentlevel())) {
            ArrayList<Word> failedLevelList = _failedList.get(Level.getCurrentlevel());
            if (!failedLevelList.contains(word)) {
                failedLevelList.add(word);
            }
        } else {
            ArrayList<Word> failedLevelList = new ArrayList<Word>();
            failedLevelList.add(word);
            _failedList.put(Level.getCurrentlevel(), failedLevelList);
        }

    }

    public ArrayList<Word> levelListForRevise() {

        if (_failedList.containsKey(Level.getCurrentlevel())) {

            ArrayList failedLevel = _failedList.get(Level.getCurrentlevel());
            long seed = System.nanoTime();
            Collections.shuffle(failedLevel, new Random(seed));

            return failedLevel;
        } else {
            return null;
        }
    }

    public void removeFromLevel(Word word) {

        ArrayList<Word> levelList = _failedList.get(Level.getCurrentlevel());

        if (levelList.contains(word)) {
            levelList.remove(word);
        }
    }

    public void printfailed2() {
        if (_failedList.containsKey(2)) {
            ArrayList<Word> list = _failedList.get(2);

            for (int i = 1; i < list.size(); i++) {

            }
        }



    }

    public boolean checkAnyWords(int level) {

        if (_failedList.containsKey(level)) {
            if(_failedList.get(level).size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void clearFailed() {
        _failedList = new HashMap<Integer,ArrayList<Word>>();
    }




}
