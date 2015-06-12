package sample.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import sample.business.Question;
import sample.business.QuestionFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Controller {


    static boolean rightAnswer;
    public AnswerPane answerPane = new AnswerPane();
    public GridPane mainPane;
    public GridPane jokerPane = new GridPane();
    public Text questionText = new Text();
    public Text showMoney = new Text();
    public Text showQuestionNumber = new Text();
    int money = 0;
    int questionNumber = 0;
    Button fiftyfifty = new Button();
    Button stopGame = new Button();
    Button audienceJoker = new Button();
    Button restartGame = new Button();

    public void startGame() {  // Startet das Spiel durch Dr�cken des Startbuttons
        initializeBoard();
        loadNextQuestion();
        addJokerButtons();

    }

    public void initializeBoard() {
        mainPane.getChildren().clear();
        mainPane.add(showQuestionNumber, 0, 0);
        mainPane.add(showMoney, 0, 1);
        mainPane.add(questionText, 0, 2);
        mainPane.add(answerPane, 0, 3);
        mainPane.add(jokerPane, 0, 4);
        money = 0;
        questionNumber = 0;
    }

    public void addRestartButton() {
        mainPane.add(restartGame, 0, 4);
        restartGame.setText("Neues Spiel");
        restartGame.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                QuestionFactory.restart();
                startGame();

            }
        });
    }

    public void addJokerButtons() {
        jokerPane.getChildren().clear();
        fiftyfifty.setDisable(false);
        stopGame.setDisable(false);
        audienceJoker.setDisable(false);
        jokerPane.add(fiftyfifty, 0, 0);
        fiftyfifty.setText("Fuenfzig/Fuenfzig");
        jokerPane.add(stopGame, 1, 0);
        stopGame.setText("Aufhoeren");
        jokerPane.add(audienceJoker, 0, 1);
        audienceJoker.setText("Publikumsjoker");

        stopGame.setOnAction(event -> {
            mainPane.getChildren().clear();
            Text t = new Text();
            Text t2 = new Text();
            mainPane.add(t, 0, 1);
            mainPane.add(t2, 0, 2);
            t.setText("Spiel beendet!");
            t2.setText("Sie haben " + questionNumber + " Frage(n) gespielt und " + money + " Euro gewonnen!");
            addRestartButton();

        });

        fiftyfifty.setOnAction(event -> {
            List<AnswerButton> wrongAnswerButtons = answerPane.getChildren().stream()
                    .filter(node -> node instanceof AnswerButton)
                    .map(node -> (AnswerButton) node)
                    .filter(answerButton -> !answerButton.hasCorrectAnswer())
                    .collect(Collectors.toList());
            Collections.shuffle(wrongAnswerButtons);
            answerPane.getChildren().remove(wrongAnswerButtons.get(0));
            answerPane.getChildren().remove(wrongAnswerButtons.get(1));
            fiftyfifty.setDisable(true);
        });

        audienceJoker.setOnAction(event -> {
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
                    .filter(answerButton -> answerButton.hasCorrectAnswer())
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
            audienceJoker.setDisable(true);

        });

    }

    public void loadNextQuestion() { // Die n�chste Frage wird geladen, Kontostandanzeige wird aktualisiert.

        questionNumber++;
        if (money == 500 || money == 16000) {
            showMoney.setText("Kontostand: " + money + "  Sicherheitsstufe erreicht!");
        } else {
            showMoney.setText("Kontostand: " + money);
        }
        showQuestionNumber.setText("Frage " + questionNumber);
        removeListener();
        answerPane.setDisable(false);
        answerPane.getChildren().clear();


        Question question = QuestionFactory.getRandomQuestion(questionNumber);
        questionText.setText(question.getQuestion());

        List<Position> positions = Arrays.asList(Position.values());
        Collections.shuffle(positions);
        for (int answerCounter = 0; answerCounter < question.getAnswers().size(); answerCounter++) {
            AnswerButton answerButton = new AnswerButton(question.getAnswers().get(answerCounter));
            answerPane.add(answerButton, positions.get(answerCounter).getColumnIndex(), positions.get(answerCounter).getRowIndex());

        }
        activateListener();
    }


    ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {   //Dieser Block wird nach anklicken einer Antwort ausgef�hrt.
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

            if (rightAnswer) {  // Antwort ist richtig, Kontostand wird aktualisiert, Methode loadNextQuestion wird aufgerufen

                long millisToWait = 1000;
                long millis = System.currentTimeMillis();
                while ((System.currentTimeMillis() - millis) < millisToWait) {
                    // Do nothing
                }
                switch (questionNumber) {
                    case 1:
                        money = 50;
                        break;
                    case 2:
                        money = 100;
                        break;
                    case 3:
                        money = 200;
                        break;
                    case 4:
                        money = 300;
                        break;
                    case 5:
                        money = 500;
                        break;
                    case 6:
                        money = 1000;
                        break;
                    case 7:
                        money = 2000;
                        break;
                    case 8:
                        money = 4000;
                        break;
                    case 9:
                        money = 8000;
                        break;
                    case 10:
                        money = 16000;
                        break;
                    case 11:
                        money = 32000;
                        break;
                    case 12:
                        money = 64000;
                        break;
                    case 13:
                        money = 125000;
                        break;
                    case 14:
                        money = 500000;
                        break;
                    case 15:
                        money = 1000000;
                        break;
                }

                if (questionNumber == 15) {  //Falls 15 Fragen richtig beantwortet wurden, wird das Spiel beendet.

                    mainPane.getChildren().clear();
                    Text t = new Text();
                    Text t2 = new Text();
                    Text t3 = new Text();
                    mainPane.add(t, 0, 1);
                    mainPane.add(t2, 0, 2);
                    mainPane.add(t3, 0, 3);
                    t.setText("Spiel beendet!");
                    t2.setText("Sie haben alle 15 Fragen richtig beantwortet");
                    t3.setText("und 1.000.000 Euro gewonnen!");
                    addRestartButton();

                } else {
                    loadNextQuestion();
                }

            } else {  // Wenn die ausgew�hlte Antwort falsch ist, wird das Spiel beendet.
                long millisToWait = 1000;
                long millis = System.currentTimeMillis();
                while ((System.currentTimeMillis() - millis) < millisToWait) {
                    // Do nothing
                }
                mainPane.getChildren().clear();
                Text text1 = new Text();
                Text t2 = new Text();
                mainPane.add(text1, 0, 1);
                mainPane.add(t2, 0, 2);
                text1.setText("Die Antwort war leider falsch. Spiel beendet!");
                int prize;
                if (money >= 16000) {
                    prize = 16000;
                } else if (money >= 500) {
                    prize = 500;
                } else {
                    prize = 0;
                }
                t2.setText("Sie haben " + questionNumber + " Frage(n) gespielt und " + prize + " Euro gewonnen!");
                addRestartButton();
            }

        }
    };

    public void activateListener() {
        answerPane.disableProperty().addListener(listener);
    }

    public void removeListener() {
        answerPane.disableProperty().removeListener(listener);
    }


}
