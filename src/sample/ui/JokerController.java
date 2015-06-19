package sample.ui;

import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import sample.business.QuestionFactory;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class JokerController {

    public AnswerPane answerPane;
    public GridPane jokerPane;

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

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String, Number> bc = new BarChart<>(xAxis, yAxis);
        jokerPane.add(bc, 0, 0);
        bc.setTitle("Publikumsjoker");
        xAxis.setLabel("Antwort");
        yAxis.setLabel("Wahrscheinlichkeit");

        XYChart.Series series = new XYChart.Series();
        series.setName("answers");


        AnswerButton rightAnswerButton = answerPane.getChildren().stream()
                .filter(node -> node instanceof AnswerButton)
                .map(node -> (AnswerButton) node)
                .filter(AnswerButton::hasCorrectAnswer)
                .findFirst().get();
        currentPercentage = random.nextInt(randomMax) + currentLevelMinPercentage;
        modulo = modulo - currentPercentage;

        series.getData().add(new XYChart.Data(rightAnswerButton.getPosition().name(), currentPercentage));

        List<AnswerButton> wrongAnswerButtons = answerPane.getChildren().stream()
                .filter(node -> node instanceof AnswerButton)
                .map(node -> (AnswerButton) node)
                .filter(answerButton -> !answerButton.hasCorrectAnswer())
                .collect(Collectors.toList());
        Collections.shuffle(wrongAnswerButtons);

        for (AnswerButton wrongAnswerButton : wrongAnswerButtons) {
            if (modulo != 0) {
                currentPercentage = random.nextInt(modulo) + 1;
                modulo = modulo - currentPercentage;
                series.getData().add(new XYChart.Data(wrongAnswerButton.getPosition().name(), currentPercentage));
            } else {
                series.getData().add(new XYChart.Data(wrongAnswerButton.getPosition().name(), 0));
            }
        }

        series.getData().sort((first, second) -> Collator.getInstance(Locale.GERMANY).compare(((XYChart.Data) first).getXValue(), ((XYChart.Data) second).getXValue()));

        bc.getData().add(series);
        bc.setLegendVisible(false);
    }

    public void setAnswerPane(AnswerPane answerPane) {
        this.answerPane = answerPane;
    }
}
