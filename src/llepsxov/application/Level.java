package llepsxov.application;

/**
 * Representation of word levels in the VOXSPELL application
 */
public class Level {

    private static int _levelUnlocked = 0;
    private static int _currentLevel = 0;

    /**
     * sets the current level the application is associating with
     * @param level
     */
    public static void setLevel(int level) {
        _currentLevel = level;
    }

    /**
     * returns the current level the application is associating with
     * @return
     */
    public static int getCurrentlevel() {
        return _currentLevel;
    }

    /**
     * specifies what levels are unlockable
     * @param level
     */
    public static void setUnlockedlevel(int level) {

        if (level > _levelUnlocked) { // only change if level is greater than the levelUnlocked
            _levelUnlocked = level;
        }
    }

    /**
     * returns the unlockedLevel
     * @return
     */
    public static int getUnlockedlevel() {
        return _levelUnlocked;
    }

    /**
     * unlocks the next level
     */
    public static void nextlevelUnlocked() {

        if (_levelUnlocked < 10) {
            _levelUnlocked++;
        }
    }

    /**
     * unlocks the next level
     */
    public static void nextLevel() {

        if (_currentLevel == _levelUnlocked) {
            _levelUnlocked++;
        }
        _currentLevel++;
    }


}
