package ir.ac.kntu.menus;

import ir.ac.kntu.Manager;
import ir.ac.kntu.helpers.Colors;
import ir.ac.kntu.helpers.Errors;
import ir.ac.kntu.helpers.MenuLevel;
import ir.ac.kntu.helpers.ScannerWrapper;
import ir.ac.kntu.models.*;

import java.util.ArrayList;

public class AnswersMenu {
    private User currentUser;


    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    private Answer currentAnswer;

    public AnswersMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        currentHomework = Manager.getInstance().getCurrentHomework();
        currentQuestion = Manager.getInstance().getCurrentQuestion();
        printMenu();
    }

    public void printMenu() {
        ArrayList<Answer> answers;
        if (currentCourse.isOwner(currentUser) && currentQuestion == null) {
            answers = currentHomework.getFinalAnswers();
            printOwnerText(false);
        } else if (currentCourse.isOwner(currentUser)) {
            answers = currentHomework.getFinalAnswers(currentQuestion);
            printOwnerText(true);
        } else if (currentQuestion == null) {
            answers = currentHomework.getAnswers(currentUser);
            printUserText(false);
        } else {
            answers = currentHomework.getAnswers(currentUser, currentQuestion);
            printUserText(true);
        }
        printActualMenu(answers);
    }

    private void printOwnerText(boolean hasQuestion) {
        if (hasQuestion) {
            System.out.println(Colors.LIGHT_BLUE + "Final answers for question " + Colors.ORANGE + currentQuestion.getName() + Colors.RESET);
        } else {
            System.out.println(Colors.LIGHT_BLUE + "Final answers for homework " + Colors.ORANGE + currentHomework.getName() + Colors.RESET);
        }
    }

    private void printUserText(boolean hasQuestion) {
        if (hasQuestion) {
            System.out.println(Colors.LIGHT_BLUE + "Answers for question " + Colors.ORANGE + currentQuestion.getName() + Colors.RESET);
        } else {
            System.out.println(Colors.LIGHT_BLUE + "Answers for homework " + Colors.ORANGE + currentHomework.getName() + Colors.RESET);
        }
    }

    private void printActualMenu(ArrayList<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            System.out.println(i + 1 + " - " + answers.get(i));
        }
        System.out.println(answers.size() + 1 + " - " + "Back");
//        Errors error = exportHTML(answers);
//        if (error != Errors.NO_ERROR) {
//            System.out.println(error);
//        } else {
//            System.out.println(Colors.GREEN + "Exported to html" + Colors.RESET);
//        }
        int option = ScannerWrapper.nextInt();
        if (option > answers.size() + 1 || option < 1) {
            System.out.println(Errors.INVALID_INPUT);
            printMenu();
            return;
        } else if (option == answers.size() + 1) {
            if (currentCourse.isOwner(currentUser) && currentQuestion == null) {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_HOMEWORK_MENU);
            } else if (currentCourse.isOwner(currentUser)) {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_QUESTION_MENU);
            } else if (currentQuestion == null) {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_HOMEWORK_MENU);
            } else {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_QUESTION_MENU);
            }
            return;
        }
        currentAnswer = answers.get(option - 1);
        Manager.getInstance().setCurrentAnswer(currentAnswer);
        if (currentCourse.isOwner(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.OTHER_ANSWER_MENU);
        } else {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_ANSWER_MENU);
        }
    }
}
