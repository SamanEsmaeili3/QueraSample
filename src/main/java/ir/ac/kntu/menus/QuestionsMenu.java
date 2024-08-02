package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.helpers.*;
import ir.ac.kntu.models.*;

public class QuestionsMenu implements Menu{
    private User currentUser;

    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    public QuestionsMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        currentHomework = Manager.getInstance().getCurrentHomework();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.ORANGE + currentHomework.getName() + Colors.LIGHT_BLUE + "'s Questions" + Colors.RESET);
        ArrayList<Question> questions = currentHomework.getQuestions();
        ArrayList<Answer> answers = currentHomework.getFinalAnswers(currentUser);
        for (int i = 0; i < questions.size(); i++) {
            System.out.println(i + 1 + " - " + reprQuestion(answers, questions.get(i)));
        }
        System.out.println(questions.size() + 1 + " - " + "Back");
        int option = ScannerWrapper.nextInt();
        if (option > questions.size() + 1 || option < 1) {
            System.out.println(Errors.INVALID_INPUT);
            printMenu();
            return;
        } else if (option == questions.size() + 1) {
            if (currentCourse.isOwner(currentUser)) {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_HOMEWORK_MENU);
            } else {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_HOMEWORK_MENU);
            }
            return;
        }
        currentQuestion = questions.get(option - 1);
        Manager.getInstance().setCurrentQuestion(currentQuestion);
        if (currentCourse.isOwner(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_QUESTION_MENU);
        } else {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_QUESTION_MENU);
        }
    }

    private String reprQuestion(ArrayList<Answer> answers, Question question) {
        for (Answer answer : answers) {
            if (answer.getQuestion().equals(question) && answer.isScored()) {
                return question + " " + Colors.GREEN + "(" + answer.getFinalScore() + "/" + question.getScore() + ")" + Colors.RESET;
            }
        }
        return question.toString();
    }
}
