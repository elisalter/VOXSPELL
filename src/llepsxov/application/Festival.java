package llepsxov.application;

import java.io.*;

/**
 * Used for all festival related calls in a static related context
 */
public class Festival {

    //sets default voice instance
    private static String _currentVoice = "voice_kal_diphone";

    /**
     * returns a string representation of current voice
     * @return _currentVoice
     */
    public static String voice() {
        return _currentVoice;
    }

    /**
     * sets the voice
     * @param voice
     */
    public static void setVoice(String voice) {
        _currentVoice = voice; // sets field
    }


    /**
     * calls festival from the Bash terminal
     * @param sayThis
     */
    public static void callFestival(String sayThis) {

        String cmd = "festival -b .festival.scm"; // command relies on the festival scm file

        writeSayThis(sayThis); // says the phrase

        ProcessBuilder speakWord = new ProcessBuilder("/bin/bash", "-c", cmd);

        try {
            Process speakWordProcess = speakWord.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * writing to .scm file for festival call
     * @param sayThis
     */
    public static void writeSayThis(String sayThis) {

        try {

            //.festival.scm file
            File file = new File(".festival.scm");

            deleteFile(); //deletes old scm file

            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);

            // specify festival voice features and write to scm file
            bw.write("(" + _currentVoice + ")");
            bw.newLine();
            bw.write("(Parameter.set 'Duration_Stretch 1.3)");
            bw.newLine();
            bw.write("(" + "SayText \""+ sayThis + "\")");
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * deleting the .scm festival file
     */
    public static void deleteFile() {

        File file = new File(".festival.scm");
        file.delete();
    }
}
