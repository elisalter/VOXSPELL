package llepsxoV;

import java.io.*;

/**
 * This class is used for all festival related calls
 * Created by zihao123yang on 21/09/16.
 */
public class Festival {

    private static String _currentVoice = "voice_kal_diphone";

    public static String voice() {
        return _currentVoice;
    }

    /*
    public static void callFestival(String sayThis) {
        String cmd = "echo " + sayThis + " | festival --tts";
        ProcessBuilder speakWord = new ProcessBuilder("/bin/bash", "-c", cmd);
        try{
            @SuppressWarnings("unused")
            Process speakWordProcess = speakWord.start();
        } catch (Exception e){}
    }
    */


    public static void setVoice(String voice) {
        _currentVoice = voice;
    }


    /**
     * festival call from bash
     * @param sayThis
     */
    public static void callFestival(String sayThis) {
        String cmd = "festival -b festival.scm";

        writeSayThis(sayThis);

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

            File file = new File("festival.scm");

            deleteFile();
            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("(" + _currentVoice + ")");
            bw.newLine();
            bw.write("(Parameter.set 'Duration_Stretch 1.2)");
            bw.newLine();
            bw.write("(" + "SayText \""+ sayThis + "\")");
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * deleting the .scm file
     */
    public static void deleteFile() {

        File file = new File("festival.scm");
        file.delete();
    }








}
