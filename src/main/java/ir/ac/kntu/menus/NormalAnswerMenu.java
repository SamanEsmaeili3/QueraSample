package ir.ac.kntu.menus;

import ir.ac.kntu.Manager;
import ir.ac.kntu.helpers.Colors;
import ir.ac.kntu.helpers.Errors;
import ir.ac.kntu.helpers.ScannerWrapper;
import ir.ac.kntu.models.*;

import java.util.ArrayList;

public class NormalAnswerMenu {
    private User currentUser;

    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    private Answer currentAnswer;

    public NormalAnswerMenu() {
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
        System.out.println(Colors.LIGHT_BLUE + " in course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Answer: " + Colors.RESET + currentAnswer.getAnswer());
        switch(MenuOptions.printMenu()) {
            case FINAL:
                makeFinal();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
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
        FINAL("Make final answer"),
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
