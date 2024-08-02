package ir.ac.kntu.models;

import java.util.ArrayList;
import java.util.Objects;

import ir.ac.kntu.helpers.*;

public class Homework {
    private String name;

    private String description;

    private DateTime startTime;

    private DateTime endTime;

    private DateTime delayTime;

    private float delayRate;

    private boolean isTestMode;

    private boolean hasScoreTable;

    private ArrayList<Question> questions;

    private ArrayList<Answer> answers;

    public Homework(String name, String description, DateTime startTime, DateTime endTime, DateTime delayTime,
                    float delayRate, boolean isTestMode, boolean hasScoreTable) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.delayTime = delayTime;
        this.delayRate = delayRate;
        this.isTestMode = isTestMode;
        this.hasScoreTable = hasScoreTable;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public Homework(String name) {
        this.name = name;
        isTestMode = false;
        hasScoreTable = true;
        questions = new ArrayList<>();
        answers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    public DateTime getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(DateTime delayTime) {
        this.delayTime = delayTime;
    }

    public float getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(float delayRate) {
        this.delayRate = delayRate;
    }

    public boolean isTestMode() {
        return isTestMode;
    }

    public void setTestMode(boolean isTestMode) {
        this.isTestMode = isTestMode;
    }

    public boolean hasScoreTable() {
        return hasScoreTable;
    }

    public void setHasScoreTable(boolean hasScoreTable) {
        this.hasScoreTable = hasScoreTable;
    }

    public ArrayList<Question> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }

    public Errors addQuestion(Question question) {
        if (questions.contains(question)) {
            return Errors.DUPLICATE_QUESTION;
        }
        questions.add(question);
        return Errors.NO_ERROR;
    }

    public Errors deleteQuestion(Question question) {
        if (!questions.contains(question)) {
            return Errors.NO_QUESTIONS;
        }
        questions.remove(question);
        return Errors.NO_ERROR;
    }

    public boolean isViewable() {
        return new DateTime().compareTo(startTime) >= 0 && !isTestMode;
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }

    public ArrayList<Answer> getAnswers(User user) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.getUsername().equals(user.getUsername())) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public ArrayList<Answer> getAnswers(Question question) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.getQuestion().equals(question)) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public ArrayList<Answer> getAnswers(User user, Question question) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.getUsername().equals(user.getUsername()) && answer.getQuestion().equals(question)) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public ArrayList<Answer> getFinalAnswers() {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.isFinal()) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public ArrayList<Answer> getFinalAnswers(User user) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.isFinal() && answer.getUsername().equals(user.getUsername())) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public ArrayList<Answer> getFinalAnswers(Question question) {
        ArrayList<Answer> answers = new ArrayList<>();
        for (Answer answer : this.answers) {
            if (answer.isFinal() && answer.getQuestion().equals(question)) {
                answers.add(answer);
            }
        }
        return answers;
    }

    public Answer getFinalAnswer(User user, Question question) {
        for (Answer answer : this.answers) {
            if (answer.isFinal() && answer.getUsername().equals(user.getUsername())
                    && answer.getQuestion().equals(question)) {
                return answer;
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Homework other = (Homework) obj;
        return name.equals(other.getName());
    }

    @Override
    public String toString() {
        return name + " [from (" + startTime + ") until (" + endTime + (isTestMode ? "), Test Mode" : ")")
                + (hasScoreTable ? "" : "Private scoreboard") + "]";
    }
}
