package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class NormalQuestionMenu implements Menu {
    private User currentUser;

    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    public NormalQuestionMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        currentHomework = Manager.getInstance().getCurrentHomework();
        currentQuestion = Manager.getInstance().getCurrentQuestion();
        printMenu();
    }

    public void printMenu() {
        System.out.print(Colors.LIGHT_BLUE + "Question " + Colors.ORANGE + currentQuestion.getName());
        System.out.print(Colors.LIGHT_BLUE + " in Homework " + Colors.ORANGE + currentHomework.getName());
        System.out.println(Colors.LIGHT_BLUE + " in course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Description: " + Colors.RESET + currentQuestion.getDescription());
        switch(MenuOptions.printMenu()) {
            case ANSWER_QUESTION:
                answerQuestion();
                break;
            case MY_ANSWERS:
                showAnswers();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void answerQuestion() {
        System.out.print("Enter your answer: ");
        String answer = ScannerWrapper.nextLine();
        DateTime sendTime = new DateTime();
        if (sendTime.compareTo(currentHomework.getDelayTime()) > 0) {
            System.out.println(Errors.ANSWER_DELAYED);
            return;
        }
        Answer newAnswer;
        float delayRate = sendTime.compareTo(currentHomework.getEndTime()) > 0 ? currentHomework.getDelayRate() : 1;
        if (currentQuestion.hasAutoScore()) {
            int score = answer.equals(currentQuestion.getAnswer()) ? currentQuestion.getScore() : 0;
            ArrayList<Answer> answers = currentHomework.getAnswers(currentUser, currentQuestion);
            boolean isFinal = true;
            for (Answer userAnswer : answers) {
                if (userAnswer.getScore() >= score * delayRate) {
                    isFinal = false;
                    break;
                }
            }
            if (isFinal) {
                unFinalPreviousAnswers();
            }
            newAnswer = new Answer(currentQuestion, currentUser.getUsername(), sendTime, answer, delayRate, score, isFinal);
            System.out.println(Colors.GREEN + "Your answer has been sent. Your score: " + (int) (score * delayRate) + "/" + currentQuestion.getScore() + Colors.RESET);
        } else {
            unFinalPreviousAnswers();
            newAnswer = new Answer(currentQuestion, currentUser.getUsername(), sendTime, answer, delayRate, true);
            System.out.println(Colors.GREEN + "Your answer has been sent." + Colors.RESET);
        }
        currentHomework.addAnswer(newAnswer);
    }

    private void unFinalPreviousAnswers() {
        ArrayList<Answer> answers = currentHomework.getAnswers(currentUser, currentQuestion);
        for (Answer answer : answers) {
            answer.setFinal(false);
        }
    }

    public void showAnswers() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.ANSWERS);
    }

    public void back() {
        Manager.getInstance().setCurrentQuestion(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.Questions);
    }

    private enum MenuOptions {
        ANSWER_QUESTION("Answer Question"),
        MY_ANSWERS("My answers"),
        BACK("Back");

        private String name;

        private MenuOptions(String name) {
            this.name = name;
        }

        public static MenuOptions printMenu() {
            MenuOptions[] options = MenuOptions.values();
            for (int i = 0; i < options.length; i++) {
                System.out.println(i + 1 + " - " + options[i]);
            }
            int choice = ScannerWrapper.nextInt();
            if (choice > options.length || choice < 1) {
                System.out.println(Errors.INVALID_INPUT);
                return printMenu();
            }
            return options[choice - 1];
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
