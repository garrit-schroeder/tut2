package sample.ui;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import sample.business.Answer;

public class AnswerButton extends Button {

    Answer answer;

    public AnswerButton(final Answer answer) {
        this.answer = answer;
        setText(answer.getText());
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (answer.isCorrect()) {
                    setStyle("-fx-background-color: #00ff3f;");
                    Controller.rightAnswer = true;

                } else {
                    setStyle("-fx-background-color: #ff0c00;");
                    Controller.rightAnswer = false;
                }

                getParent().setDisable(true);
            }
        });
    }

    public boolean hasCorrectAnswer() {
        return answer.isCorrect();
    }

}