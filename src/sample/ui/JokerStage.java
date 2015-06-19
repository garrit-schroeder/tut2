package sample.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JokerStage extends Stage {

    public JokerStage(final AnswerPane answerPane) {
        try {
            this.setTitle("Publikumsjoker");
            final FXMLLoader loader = new FXMLLoader(getClass().getResource("joker.fxml"));
            this.setScene(new Scene(loader.load(), 100, 100));
            final JokerController jokerController = loader.getController();
            jokerController.setAnswerPane(answerPane);
            jokerController.createDiagram();
            this.show();
        } catch (IOException ignored) {
        }
    }
}