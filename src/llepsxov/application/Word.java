package llepsxov.application;

import java.io.Serializable;

/**
 *  Class used for creating word objects.
 */
public class Word implements Serializable, Comparable<Word> {

    private static final long serialVersionUID = 1L;
    private String _word;
    private int _numMastered;
    private int _numFaulted;
    private int _numFailed;
    private int _level;
    private int numAppeared;

    /**
     * creates a new word with a name referring to the word, and the level it belongs in.
     * @param word
     * @param level
     */
    public Word(String word, int level) {
        _word = word;
        _level = level;
    }

    /**
     * increment times the word has been mastered
     */
    public void addMastered() {

        _numMastered++;
    }

    /**
     * incrememt the times the word has been faulted
     */
    public void addFaulted() {

        _numFaulted++;
    }

    /**
     * incrememnt the times the word has been failed
     */
    public void addFailed() {

        _numFailed++;
    }

    /**
     * return the name of the word
     * @return name of word
     */
    public String name() {

        return _word;
    }

    /**
     * compares two words together, used for sorting
     *
     * @param o = word to compare with this
     * @return int -1 if less than 0 if equal 1 if greater
     */
    @Override
    public int compareTo(Word o) {
        return _word.compareTo(o._word);
    }

    /**
     * overrides the toString() method
     * @return name of this word
     */
    @Override
    public String toString(){
        return this._word;
    }

    /**
     * overrides the equals() method
     * @param word, used for equality check
     * @return true or false
     */
    @Override
    public boolean equals(Object word){

        return this._word.equals(((Word)word)._word);
    }


    //=================================================================================================================
    // getters and setters below

    public int getLevel(){
        return this._level;
    }

    public int getNumMastered(){
        return _numMastered;
    }

    public int getNumFaulted(){
        return _numFaulted;
    }

    public int getNumFailed(){
        return _numFailed;
    }

    public String get_word() {
        return _word;
    }

    public void set_word(String _word) {
        this._word = _word;
    }

    public int getNumAppeared() {
        return _numMastered + _numFaulted + _numFailed;
    }

    public void setNumAppeared(int numAppeared) {
        this.numAppeared = numAppeared;
    }

}

