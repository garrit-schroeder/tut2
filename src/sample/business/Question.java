package sample.business;

import java.util.List;

public class Question {

    final String question;
    final int level;
    final List<Answer> answers;
    final int id;

    public Question(String question, int id, int level,List<Answer> answers) {
        this.question = question;
        this.answers = answers;
        this.level = level;
        this.id = id;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public int getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}