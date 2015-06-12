package sample.business;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class QuestionFactory {

    static int level;
    static int[] usedQuestions = new int[15];
    static int counter = 0;
    static final List<Question> questions = Arrays.asList(
            new Question("", 1, 1, Arrays.asList(
                    new Answer("A", false), new Answer("Dies ist richtig", true), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 2", 2, 1, Arrays.asList(
                    new Answer("Richtig", true), new Answer("B", false), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 3", 3, 1, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("C", false), new Answer("Richtig", true))),
            new Question("Frage 4", 4, 2, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("Richtig", true), new Answer("D", false))),
            new Question("Frage 5", 5, 2, Arrays.asList(
                    new Answer("A", false), new Answer("Richtig", true), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 6", 6, 2, Arrays.asList(
                    new Answer("Richtig", true), new Answer("B", false), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 7", 7, 3, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("C", false), new Answer("Richtig", true))),
            new Question("Frage 8", 8, 3, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("Richtig", true), new Answer("D", false))),
            new Question("Frage 9", 9, 3, Arrays.asList(
                    new Answer("A", false), new Answer("Richtig", true), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 10", 10, 4, Arrays.asList(
                    new Answer("Richtig", true), new Answer("B", false), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 11", 11, 4, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("C", false), new Answer("Richtig", true))),
            new Question("Frage 12", 12, 4, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("Richtig", true), new Answer("D", false))),
            new Question("Frage 13", 13, 5, Arrays.asList(
                    new Answer("A", false), new Answer("Richtig", true), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 14", 14, 5, Arrays.asList(
                    new Answer("Richtig", true), new Answer("B", false), new Answer("C", false), new Answer("D", false))),
            new Question("Frage 15", 15, 5, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("C", false), new Answer("Richtig", true))),
            new Question("Frage 16", 16, 5, Arrays.asList(
                    new Answer("A", false), new Answer("B", false), new Answer("Richtig", true), new Answer("D", false)))

    );

    private QuestionFactory() {
        super();
    }

    public static Question getRandomQuestion(int questionNumber) {


        if (questionNumber <= 3) {
            level = 1;
        } else if (questionNumber <= 6) {
            level = 2;
        } else if (questionNumber <= 9) {
            level = 3;
        } else if (questionNumber <= 12) {
            level = 4;
        } else {
            level = 5;
        }

        List<Question> currentLevelQuestions = questions.stream()
                .filter(question -> question.getLevel() == level)
                .collect(Collectors.toList());


        int random = new Random().nextInt(currentLevelQuestions.size());
        for (int i = 0; i < usedQuestions.length; i++) {
            if (currentLevelQuestions.get(random).getId() == usedQuestions[i]) {
                random = new Random().nextInt(currentLevelQuestions.size());
                i = -1;
            }
        }
        usedQuestions[counter] = currentLevelQuestions.get(random).getId();
        counter++;
        return currentLevelQuestions.get(random);

    }

    public static void restart() {
        Arrays.fill(usedQuestions, 0);
        counter = 0;
    }

    public static int getLevel() {
        return level;
    }
}