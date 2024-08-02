package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class OtherAnswerMenu implements Menu {
    private User currentUser;

    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    private Answer currentAnswer;

    public OtherAnswerMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        currentHomework = Manager.getInstance().getCurrentHomework();
        currentAnswer = Manager.getInstance().getCurrentAnswer();
        currentQuestion = currentAnswer.getQuestion();
        printMenu();
    }

    public void printMenu() {
        System.out.print(Colors.LIGHT_BLUE + "Answer for " + Colors.ORANGE + currentQuestion.getName());
        System.out.print(Colors.LIGHT_BLUE + " in Homework " + Colors.ORANGE + currentHomework.getName());
        System.out.print(Colors.LIGHT_BLUE + " in course " + Colors.ORANGE + currentCourse.getName());
        System.out.println(Colors.LIGHT_BLUE + " by " + Colors.ORANGE + currentUser.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Answer: " + Colors.RESET + currentAnswer.getAnswer());
        switch(MenuOptions.printMenu()) {
            case SCORE:
                score();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void score() {
        if (currentAnswer.isScored()) {
            System.out.println(Errors.ANSWER_ALREADY_SCORED);
            return;
        }
        System.out.println("User answer: " + currentAnswer.getAnswer());
        System.out.print("Enter score: (0 - " + currentQuestion.getScore() + ") ");
        int score = ScannerWrapper.nextInt(0, currentQuestion.getScore());
        currentAnswer.setScore(score);
        currentAnswer.setFinalScore((int) (score * currentAnswer.getDelayRate()));
        currentAnswer.setScored(true);
        System.out.println(Colors.GREEN + "Score saved" + Colors.RESET);
    }

    public void makeFinal() {
        unFinalPreviousAnswers();
        currentAnswer.setFinal(true);
        System.out.println(Colors.GREEN + "Answer marked as final" + Colors.RESET);
    }

    private void unFinalPreviousAnswers() {
        ArrayList<Answer> answers = currentHomework.getAnswers(currentUser, currentQuestion);
        for (Answer answer : answers) {
            answer.setFinal(false);
        }
    }

    public void back() {
        Manager.getInstance().setCurrentAnswer(null);
        Manager.getInstance().setCurrentAccessLevel(Manager.getInstance().getLastAccessLevel());
    }

    private enum MenuOptions {
        SCORE("Score answer"),
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
