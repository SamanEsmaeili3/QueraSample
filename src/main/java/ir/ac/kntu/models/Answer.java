package ir.ac.kntu.models;

import ir.ac.kntu.helpers.DateTime;

public class Answer {
    private Question question;

    private String username;

    private DateTime sendTime;

    private String answer;

    private float delayRate;

    private int score;

    private int finalScore;

    private boolean isFinal;

    private boolean scored;

    public Answer(Question question, String username, DateTime sendTime, String answer, float delayRate, int score, boolean isFinal) {
        this.question = question;
        this.username = username;
        this.sendTime = sendTime;
        this.answer = answer;
        this.delayRate = delayRate;
        this.score = score;
        this.finalScore = (int) (score * delayRate);
        this.isFinal = isFinal;
        this.scored = true;
    }

    public Answer(Question question, String username, DateTime sendTime, String answer, float delayRate, boolean isFinal) {
        this.question = question;
        this.username = username;
        this.sendTime = sendTime;
        this.answer = answer;
        this.delayRate = delayRate;
        this.isFinal = isFinal;
        this.scored = false;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public DateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(DateTime sendTime) {
        this.sendTime = sendTime;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public float getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(float delayRate) {
        this.delayRate = delayRate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }

    public boolean isScored() {
        return scored;
    }

    public void setScored(boolean scored) {
        this.scored = scored;
    }

//    public String toHTML() {
//        if (!scored) {
//            return "<tr><td>" + username + "</td><td>" + question.getName() + "</td><td>" + sendTime.toString()
//                    + "</td><td> - </td><td> - </td><td>" + isFinal + "</td></tr>";
//        }
//        return "<tr><td>" + username + "</td><td>" + question.getName() + "</td><td>" + sendTime.toString() + "</td><td>" + score
//                + "</td><td>" + finalScore + "</td><td>" + isFinal + "</td></tr>";
//    }

    @Override
    public String toString() {
        if (!scored) {
            return "User: " + username + ", Question: " + question.getName() + " [sendTime: " + sendTime + "]" + (isFinal ? " (final answer)" : "") + " (Not Scored)";
        }
        return "User: " + username + ", Question: " + question.getName() + " [score: " + score + ", final score: " + score + ", sendTime: " + sendTime + "]" + (isFinal ? " (final answer)" : "");
    }
}
