package llepsxov.application;

/**
 * a class dedicated to updating Word object data, and calculating accuracy statistics from a database of words
 */
public class Statistics {

    int _numWords;
    int _mastered;
    int _faulted;
    int _failed;
    int _wordsTested;
    DataBase _db = DataBase.getInstance();


    /**
     * initialises the statistics object
     */
    public Statistics() {
        _numWords = 0;
        _mastered = 0;
        _faulted = 0;
        _failed = 0;
        _wordsTested = 0;
    }

    /**
     * sets number of words
     * @param num
     */
    public void setNumWords(int num) {
        _numWords = num;
    }


    /**
     * increments the number of times a word has been mastered and the times it's been tested by 1
     */
    public void increaseMastered() {
        _mastered++;
        _wordsTested++;
    }

    /**
     * increments the number of times a word has been faulted and the times it's been tested by 1
     */
    public void increaseFaulted() {
        _faulted++;
        _wordsTested++;
    }

    /**
     * increments the number of times a word has been and the times it's been tested by 1
     */
    public void increaseFailed() {
        _failed++;
        _wordsTested++;
    }

    /**
     * returns accuarcy as a percentage
     * @return
     */
    public double calculateAccurracy() {

        return Math.round(((double)_mastered)/((double)_wordsTested)*100);

    }


    /**
     * returns a boolean value to indicate if the current level has been passed at the end of a test
     * @return
     */
    public boolean levelPassed() {
        if (_mastered >= 9) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * takes in one integer parameter level, then calculates the cumulative accuracy for all words that have been tested
     * with the same level.
     * @param level
     * @return
     */
    public double calculateLevelAccuracy(int level) {

        int numMasteredOnLevel = 0;
        int wordsAppearedOnLevel = 0;



        for(Word word : _db.getStoredStats()){ // for every word in stats

            if(word.getLevel() == level){ // if the words level is relevant

                wordsAppearedOnLevel += word.getNumFailed() + word.getNumFaulted() + word.getNumMastered();
                numMasteredOnLevel += word.getNumMastered();

            }
        }

        if (wordsAppearedOnLevel == 0){ // if no words have appeared in current level set accuracy to default of zero
            return 100.0;
        }

        return Math.round(((double)numMasteredOnLevel)/((double)wordsAppearedOnLevel)*100);

    }

}
