package ir.ac.kntu.models;

import java.util.Comparator;
import java.util.Objects;

public class Question {
    private String name;

    private int score;

    private String description;

    private QuestionLevel level;

    private QuestionType type;

    private String answer;

    private boolean autoScore;

    public Question(String name, int score, String description, QuestionLevel level,
                    QuestionType type, String answer) {
        this.name = name;
        this.score = score;
        this.description = description;
        this.level = level;
        this.type = type;
        this.answer = answer;
        this.autoScore = false;
    }

    public Question(String name) {
        this.name = name;
        this.autoScore = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QuestionLevel getLevel() {
        return level;
    }

    public void setLevel(QuestionLevel level) {
        this.level = level;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean hasAutoScore() {
        return autoScore;
    }

    public void sethasAutoScore(boolean autoScore) {
        this.autoScore = autoScore;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Question other = (Question) obj;
        return name.equals(other.getName()) && level == other.getLevel() && type == other.getType();
    }

    @Override
    public String toString() {
        return name + "[level: " + level + ", type: " + type  + ", score: " + score + (autoScore ? ", Auto Score" : "") + "]";
    }

    public static class SortByDifficulty implements Comparator<Question> {
        @Override
        public int compare(Question q1, Question q2) {
            return q1.getLevel().compareTo(q2.getLevel());
        }
    }
}

