package sample.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import sample.business.Question;
import sample.business.QuestionFactory;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    static boolean rightAnswer;
    public AnswerPane answerPane = new AnswerPane();
    public GridPane mainPane;
    public GridPane jokerPane = new GridPane();
    public Text questionText = new Text();
    public Text showMoney = new Text();
    public Text showQuestionNumber = new Text();
    public Button startButton;
    int money = 0;
    int questionNumber = 0;
    Button fiftyfifty = new Button();
    Button stopGame = new Button();
    Button audienceJoker = new Button();
    Button restartGame = new Button();
    private JokerStage jokerStage;

    public void startGame() {  // Startet das Spiel durch Dr�cken des Startbuttons
        startButton.setDisable(true);
        Image image = new Image(new File("./bild.jpg").toURI().toString());
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1205, 574, false, false, false, false));
        Background background = new Background(backgroundImage);
        mainPane.setBackground(background);
        File source = new File("./start.mp3");
        Media media = new Media(source.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setOnEndOfMedia(this::initializeBoard);
    }

    public void initializeBoard() {
        mainPane.getChildren().clear();
        GridPane pane = new GridPane();
        pane.setStyle("-fx-background-color: white");
        pane.add(showMoney, 0, 0);
        pane.add(showQuestionNumber, 0, 1);
        pane.add(questionText, 0, 2);

        mainPane.add(pane, 0, 0);
        mainPane.add(answerPane, 0, 1);
        mainPane.add(jokerPane, 0, 2);
        money = 0;
        questionNumber = 0;
        loadNextQuestion();
        addJokerButtons();
    }

    public void addRestartButton() {
        restartGame.setDisable(false);
        mainPane.add(restartGame, 0, 4);
        restartGame.setText("Neues Spiel");
        restartGame.setOnAction(event -> {
            restartGame.setDisable(true);
            QuestionFactory.restart();
            startGame();
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
        fiftyfifty.setMinWidth(170);
        stopGame.setMinWidth(170);
        audienceJoker.setMinWidth(170);

        stopGame.setOnAction(event -> {
            mainPane.getChildren().clear();
            Text t = new Text();
            Text t2 = new Text();
            GridPane pane = new GridPane();
            pane.setStyle("-fx-background-color: white");
            t.setText("Spiel beendet!");
            t2.setText("Sie haben " + questionNumber + " Frage(n) gespielt und " + money + " Euro gewonnen!");
            pane.add(t, 0, 1);
            pane.add(t2, 0, 2);
            mainPane.add(pane, 0, 0);
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
            audienceJoker.setDisable(true);
            jokerStage = new JokerStage(answerPane);
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

        try {
            File source = new File("./newQuestion.mp3");
            Media media = new Media(source.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Question question = QuestionFactory.getRandomQuestion(questionNumber);
        questionText.setText(question.getQuestion());

        List<Position> positions = Arrays.asList(Position.values());
        Collections.shuffle(positions);
        for (int answerCounter = 0; answerCounter < question.getAnswers().size(); answerCounter++) {
            AnswerButton answerButton = new AnswerButton(question.getAnswers().get(answerCounter), positions.get(answerCounter));
            answerPane.add(answerButton, positions.get(answerCounter).getColumnIndex(), positions.get(answerCounter).getRowIndex());
            answerButton.setMinWidth(170);
        }
        activateListener();
    }

    ChangeListener<Boolean> listener = new ChangeListener<Boolean>() {   //Dieser Block wird nach anklicken einer Antwort ausgef�hrt.
        @Override
        public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
            if (jokerStage != null) {
                jokerStage.close();
            }

            if (rightAnswer) {  // Antwort ist richtig, Kontostand wird aktualisiert, Methode loadNextQuestion wird aufgerufen
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
                    GridPane pane = new GridPane();
                    pane.setStyle("-fx-background-color: white");
                    t.setText("Spiel beendet!");
                    t2.setText("Sie haben alle 15 Fragen richtig beantwortet");
                    t3.setText("und 1.000.000 Euro gewonnen!");
                    pane.add(t, 0, 1);
                    pane.add(t2, 0, 2);
                    pane.add(t3, 0, 3);
                    mainPane.add(pane, 0, 0);
                    addRestartButton();

                } else {
                    loadNextQuestion();
                }

            } else {  // Wenn die ausgew�hlte Antwort falsch ist, wird das Spiel beendet.
                mainPane.getChildren().clear();
                Text text1 = new Text();
                Text t2 = new Text();

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

                GridPane pane = new GridPane();
                pane.setStyle("-fx-background-color: white");
                pane.add(text1, 0, 1);
                pane.add(t2, 0, 2);
                mainPane.add(pane, 0, 0);
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
