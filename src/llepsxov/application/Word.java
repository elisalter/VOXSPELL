package llepsxov.application;

import java.io.Serializable;

/**
 * Created by zihao123yang on 16/09/16.
 */
public class Word implements Serializable, Comparable<Word> {

    private static final long serialVersionUID = 1L;
    private String _word;
    private int _numMastered;
    private int _numFaulted;
    private int _numFailed;
    private int _level;
    private int numAppeared;

    public int getNumAppeared() {
        return _numMastered + _numFaulted + _numFailed;
    }

    public void setNumAppeared(int numAppeared) {
        this.numAppeared = numAppeared;
    }

    public Word(String word, int level) {
        _word = word;
        _level = level;
    }

    public void addMastered() {

        _numMastered++;
    }

    public void addFaulted() {

        _numFaulted++;
    }

    public void addFailed() {

        _numFailed++;
    }

    public String name() {

        return _word;
    }

    @Override
    public int compareTo(Word o) {
        return _word.compareTo(o._word);
    }

    @Override
    public String toString(){
        return this._word;
    }

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

    @Override
    public boolean equals(Object word){

        return this._word.equals(((Word)word)._word);
    }

}

