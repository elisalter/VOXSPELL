package llepsxoV;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by zihao123yang on 23/09/16.
 */
public class RevisionCompleteController {

    @FXML
    public void returnMainMenu() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenuScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }
    }

    public void retry() {
        try {
            Stage stage = Voxspell.getPrimaryStage();
            Parent root = FXMLLoader.load(getClass().getResource("MainMenuScene.fxml"));
            stage.setScene(new Scene(root, 600, 400));
            stage.show();
        } catch (Exception e) {

        }
    }
}
