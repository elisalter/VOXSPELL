package llepsxov.scenes.controllers;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import llepsxov.application.Level;
import llepsxov.application.Voxspell;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by zihao123yang on 22/09/16.
 */
public class VideoSceneController implements Initializable {


    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    private Media media;


    public void playButtonPressed(ActionEvent event) {
        mediaPlayer.play();
    }

    public void pauseButtonPressed(ActionEvent event) {
        mediaPlayer.pause();
    }

    public void stopButtonPressed(ActionEvent event) throws IOException {
        mediaPlayer.stop();

        Level.nextLevel();
        Stage stage = Voxspell.getPrimaryStage();
        Parent root = FXMLLoader.load(getClass().getResource("/llepsxov/scenes/LevelComplete.fxml"));
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public void muteButtonPressed(ActionEvent event) {
        //mediaPlayer.mute();
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String path = new File("src/llepsxov/scenes/resources/videos/big_buck_bunny_1_minute.mp4").getAbsolutePath();

        media = new Media(new File(path).toURI().toString());
        mediaPlayer = new MediaPlayer(media);

        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setAutoPlay(true);

        DoubleProperty width = mediaView.fitWidthProperty();
        DoubleProperty height = mediaView.fitHeightProperty();
        width.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));

    }
}
