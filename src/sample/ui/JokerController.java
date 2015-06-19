package sample.ui;

import javafx.scene.layout.GridPane;
import sample.business.QuestionFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class JokerController {

    public AnswerPane answerPane;

    public void createDiagram() {
        Random random = new Random();
        int modulo = 100;
        int currentPercentage;
        int level = QuestionFactory.getLevel();
        int currentLevelMinPercentage;
        int randomMax;

        if (level == 1) {
            currentLevelMinPercentage = 90 + 1;
            randomMax = 10;
        } else if (level == 2) {
            currentLevelMinPercentage = 70 + 1;
            randomMax = 30;
        } else if (level == 3) {
            currentLevelMinPercentage = 60 + 1;
            randomMax = 40;
        } else if (level == 4) {
            currentLevelMinPercentage = 50 + 1;
            randomMax = 50;
        } else {
            currentLevelMinPercentage = 40 + 1;
            randomMax = 60;
        }

        List<AnswerButton> rightAnswerButton = answerPane.getChildren().stream()
                .filter(node -> node instanceof AnswerButton)
                .map(node -> (AnswerButton) node)
                .filter(AnswerButton::hasCorrectAnswer)
                .collect(Collectors.toList());
        currentPercentage = random.nextInt(randomMax) + currentLevelMinPercentage;
        modulo = modulo - currentPercentage;
        rightAnswerButton.get(0).setText(rightAnswerButton.get(0).getText() + "  (" + currentPercentage + "%)");

        List<AnswerButton> wrongAnswerButtons = answerPane.getChildren().stream()
                .filter(node -> node instanceof AnswerButton)
                .map(node -> (AnswerButton) node)
                .filter(answerButton -> !answerButton.hasCorrectAnswer())
                .collect(Collectors.toList());
        Collections.shuffle(wrongAnswerButtons);

        for (int i = 0; i < wrongAnswerButtons.size() - 1; i++) {
            if (modulo != 0) {
                currentPercentage = random.nextInt(modulo) + 1;
                modulo = modulo - currentPercentage;
                wrongAnswerButtons.get(i).setText(wrongAnswerButtons.get(i).getText() + "  (" + currentPercentage + "%)");
            } else {
                wrongAnswerButtons.get(i).setText(wrongAnswerButtons.get(i).getText() + "  (0%");
            }
        }
        if (modulo != 0) {
            wrongAnswerButtons.get(wrongAnswerButtons.size() - 1).setText(wrongAnswerButtons.get(wrongAnswerButtons.size() - 1).getText() + "  (" + modulo + "%)");
        } else {
            wrongAnswerButtons.get(wrongAnswerButtons.size() - 1).setText(wrongAnswerButtons.get(wrongAnswerButtons.size() - 1).getText() + "  (" + 0 + "%)");
        }
    }

    public void setAnswerPane(AnswerPane answerPane) {
        this.answerPane = answerPane;
    }
}
