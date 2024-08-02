package ir.ac.kntu;

import ir.ac.kntu.helpers.MenuLevel;
import ir.ac.kntu.menus.*;
import ir.ac.kntu.models.*;

public class Manager {
    private static Manager instance = new Manager();

    private Quera quera;

    private User currentUser = null;

    private Course currentCourse = null;

    private Homework currentHomework = null;

    private Question currentQuestion = null;

    private Answer currentAnswer = null;

    private MenuLevel currentAccessLevel = MenuLevel.WITHOUT_USER;

    private MenuLevel lastAccessLevel = MenuLevel.WITHOUT_USER;

    private Manager() {
        quera = new Quera();
    }

    public static Manager getInstance() {
        return instance;
    }

    public void start() {
        while (currentAccessLevel != MenuLevel.EXIT) {
            printMenu(currentAccessLevel);
        }
    }

    public void printMenu(MenuLevel menuLevel) {
        switch (menuLevel) {
            case WITHOUT_USER -> new NoUserMenu();
            case MAIN_MENU -> new MainMenu();
            case COURSES -> new CoursesMenu();
            case SEARCH -> new SearchMenu();
            case USER -> new UserMenu();
            case OWNED_COURSE_MENU -> new OwnedCourseMenu();
            case NORMAL_COURSE_MENU -> new NormalCourseMenu();
            case UNPARTICIPATED_COURSE_MENU -> new UnparticipatedCourseMenu();
            case HOMEWORKS -> new HomeworksMenu();
            case OWNED_HOMEWORK_MENU -> new OwnedHomeworkMenu();
            case NORMAL_HOMEWORK_MENU -> new NormalHomeworkMenu();
            case Questions -> new QuestionsMenu();
            case OWNED_QUESTION_MENU -> new OwnedQuestionMenu();
            case NORMAL_QUESTION_MENU -> new NormalQuestionMenu();
            case ANSWERS -> new AnswersMenu();
            case NORMAL_ANSWER_MENU -> new NormalAnswerMenu();
            case OTHER_ANSWER_MENU -> new OtherAnswerMenu();
            default -> System.out.println("Invalid");
        }
    }

    public void setQuera(Quera quera) {
        this.quera = quera;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void setCurrentCourse(Course course) {
        currentCourse = course;
    }

    public void setCurrentHomework(Homework homework) {
        currentHomework = homework;
    }

    public void setCurrentQuestion(Question question) {
        currentQuestion = question;
    }

    public void setCurrentAnswer(Answer answer) {
        currentAnswer = answer;
    }

    public void setCurrentAccessLevel(MenuLevel accessLevel) {
        lastAccessLevel = currentAccessLevel;
        currentAccessLevel = accessLevel;
    }

    public Quera getQuera() {
        return quera;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Course getCurrentCourse() {
        return currentCourse;
    }

    public Homework getCurrentHomework() {
        return currentHomework;
    }

    public Question getCurrentQuestion() {
        return currentQuestion;
    }

    public Answer getCurrentAnswer() {
        return currentAnswer;
    }

    public MenuLevel getCurrentAccessLevel() {
        return currentAccessLevel;
    }

    public MenuLevel getLastAccessLevel() {
        return lastAccessLevel;
    }
}
