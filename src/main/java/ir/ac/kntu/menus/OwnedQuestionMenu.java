package ir.ac.kntu.menus;

import java.util.ArrayList;
import java.util.Collections;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class OwnedQuestionMenu implements Menu {
    private Quera quera;

    private Course currentCourse;

    private Homework currentHomework;

    private Question currentQuestion;

    private Answer currentAnswer;

    public OwnedQuestionMenu() {
        quera = Manager.getInstance().getQuera();
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
        switch (MenuOptions.printMenu()) {
            case ANSWERS:
                showAnswers();
                break;
            case USER_ANSWERS:
                showUserAnswers();
                break;
            case EDIT_QUESTION_DETAILS:
                editQuestionDetails();
                break;
            case DELETE_QUESTION:
                deleteQuestion();
                break;
//            case ADD_TO_BANK:
//                addToBank();
//                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void showAnswers() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.ANSWERS);
    }

    public void showUserAnswers() {
        System.out.print("Enter user email: ");
        String email = ScannerWrapper.nextLine();
        User user = quera.searchUserByEmail(email);
        if (user == null || !currentCourse.isStudent(user)) {
            System.out.println(Errors.USER_NOT_FOUND);
            return;
        }
        ArrayList<Answer> answers = currentHomework.getAnswers(user, currentQuestion);
        while (chooseAnswers(answers) == Errors.INVALID_INPUT) {
            System.out.println(Errors.INVALID_INPUT);
        }
    }

    private Errors chooseAnswers(ArrayList<Answer> answers) {
        for (int i = 0; i < answers.size(); i++) {
            System.out.println(i + 1 + " - " + answers.get(i));
        }
        System.out.println(answers.size() + 1 + " - " + Colors.GRAY + "Back" + Colors.RESET);
        int option = ScannerWrapper.nextInt();
        if (option > answers.size() + 1 || option < 1) {
            return Errors.INVALID_INPUT;
        } else if (option == answers.size() + 1) {
            return Errors.NO_ERROR;
        }
        currentAnswer = answers.get(option - 1);
        Manager.getInstance().setCurrentAnswer(currentAnswer);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.OTHER_ANSWER_MENU);
        return Errors.NO_ERROR;
    }

    public void editQuestionDetails() {
        System.out.println("Leave field blank to keep it unchanged");
        System.out.print("name from " + Colors.GRAY + currentQuestion.getName() + Colors.RESET + " to: ");
        String name = ScannerWrapper.nextLine();
        name = name.isBlank() ? currentQuestion.getName() : name;
        ArrayList<Question> questions = currentHomework.getQuestions();
        questions.remove(currentQuestion);
        for (Question question : questions) {
            if (question.getName().equals(name)) {
                System.out.println(Errors.DUPLICATE_QUESTION);
                return;
            }
        }
        currentQuestion.setName(name);
        System.out.print("score from " + Colors.GRAY + currentQuestion.getScore() + Colors.RESET + " to: ");
        String temp = ScannerWrapper.nextLine();
        currentQuestion.setScore(temp.isBlank() ? currentQuestion.getScore() : Integer.parseInt(temp));
        System.out.print("description from " + Colors.GRAY + currentQuestion.getDescription() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        currentQuestion.setDescription(temp.isBlank() ? currentQuestion.getDescription() : temp);
        System.out.print("level from " + Colors.GRAY + currentQuestion.getLevel() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        while (!temp.isBlank() && (Integer.valueOf(temp) < 1 || Integer.valueOf(temp) > 4)) {
            System.out.print("(1:Easy, 2:Medium, 3:Hard, 4:Very Hard): ");
            temp = ScannerWrapper.nextLine();
        }
        QuestionLevel level = temp.isBlank() ? currentQuestion.getLevel() : QuestionLevel.values()[Integer.parseInt(temp) - 1];
        currentQuestion.setLevel(level);
        System.out.print("type from " + Colors.GRAY + currentQuestion.getType() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        while (!temp.isBlank() && (Integer.valueOf(temp) < 1 || Integer.valueOf(temp) > 4)) {
            System.out.print("(1:Short ans, 2:Long ans, 3:Multiple choice, 4:Fill in the blank): ");
            temp = ScannerWrapper.nextLine();
        }
        QuestionType type = temp.isBlank() ? currentQuestion.getType() : QuestionType.values()[Integer.parseInt(temp) - 1];
        currentQuestion.setType(type);
        editQuestionAns();
        System.out.println(Colors.GREEN + "Question edited successfully!" + Colors.RESET);
    }

    private void editQuestionAns() {
        if (currentQuestion.getType() == QuestionType.MULTIPLE_CHOICE || currentQuestion.getType() == QuestionType.SHORT_ANSWER) {
            System.out.print("auto score? was " + currentQuestion.hasAutoScore() + " (y/n): ");
            String answer = ScannerWrapper.nextLine();
            if (answer.equals("y")) {
                boolean hadAutoScore = currentQuestion.hasAutoScore();
                currentQuestion.sethasAutoScore(true);
                if (!hadAutoScore) {
                    System.out.print("Enter answer: ");
                    currentQuestion.setAnswer(ScannerWrapper.nextLine());
                } else {
                    System.out.print("answer from " + Colors.GRAY + currentQuestion.getAnswer() + Colors.RESET + " to: ");
                    answer = ScannerWrapper.nextLine();
                    currentQuestion.setAnswer(answer.isBlank() ? currentQuestion.getAnswer() : answer);
                }
            } else if (!answer.isBlank()) {
                currentQuestion.sethasAutoScore(false);
            } else {
                if (currentQuestion.hasAutoScore()) {
                    System.out.print("answer from " + Colors.GRAY + currentQuestion.getAnswer() + Colors.RESET + " to: ");
                    answer = ScannerWrapper.nextLine();
                    currentQuestion.setAnswer(answer.isBlank() ? currentQuestion.getAnswer() : answer);
                }
            }
        } else {
            currentQuestion.sethasAutoScore(false);
            currentQuestion.setAnswer(null);
        }
        checkQuestionAns();
    }

    private void checkQuestionAns() {
        if (!currentQuestion.hasAutoScore()) {
            return;
        }
        ArrayList<Answer> answers =  currentHomework.getAnswers(currentQuestion);
        Collections.reverse(answers);
        for (Answer answer : answers) {
            if (answer.getAnswer().equals(currentQuestion.getAnswer())) {
                answer.setScore(currentQuestion.getScore());
                answer.setFinalScore((int) (answer.getScore() * answer.getDelayRate()));
                unFinalAnswers(currentHomework.getAnswers(quera.searchUserByUsername(answer.getUsername()), currentQuestion));
                answer.setFinal(true);
            } else {
                answer.setScore(0);
                answer.setFinalScore(0);
            }
        }
    }

    private void unFinalAnswers(ArrayList<Answer> answers) {
        for (Answer answer : answers) {
            answer.setFinal(false);
        }
    }

    public void deleteQuestion() {
        currentHomework.deleteQuestion(currentQuestion);
        Manager.getInstance().setCurrentQuestion(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.Questions);
    }

//    public void addToBank() {
//        if (!currentQuestion.hasAutoScore()) {
//            System.out.println(Errors.QUESTION_NOT_AUTO_SCORED);
//            return;
//        }
//        Errors error = Manager.getInstance().getQuera().getQuestionBank().addQuestion(currentQuestion);
//        if (error != Errors.NO_ERROR) {
//            System.out.println(error);
//        } else {
//            System.out.println(Colors.GREEN + "Question added to bank successfully!" + Colors.RESET);
//        }
//    }

    public void back() {
        Manager.getInstance().setCurrentQuestion(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.Questions);
    }

    private enum MenuOptions {
        ANSWERS("Answers"),
        USER_ANSWERS("Answers of a user"),
        EDIT_QUESTION_DETAILS("Edit question details"),
        DELETE_QUESTION("Delete question"),
        ADD_TO_BANK("Add to question bank"),
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
