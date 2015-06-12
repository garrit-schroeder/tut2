package sample.business;

public class Answer {

    final String text;
    final boolean correct;

    public Answer(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return correct;
    }
}
