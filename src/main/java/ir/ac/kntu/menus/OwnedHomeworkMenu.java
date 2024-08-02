package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class OwnedHomeworkMenu implements Menu {
    private Quera quera;

    private Course currentCourse;

    private Homework currentHomework;

    private Answer currentAnswer;

    public OwnedHomeworkMenu() {
        quera = Manager.getInstance().getQuera();
        currentCourse = Manager.getInstance().getCurrentCourse();
        currentHomework = Manager.getInstance().getCurrentHomework();
        printMenu();
    }

    public void printMenu() {
        System.out.print(Colors.LIGHT_BLUE + "Homework " + Colors.ORANGE + currentHomework.getName());
        System.out.println(Colors.LIGHT_BLUE + " in course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Description: " + Colors.RESET + currentHomework.getDescription());
        System.out.println(Colors.LIGHT_BLUE + "End time: " + Colors.RESET + currentHomework.getEndTime());
        System.out.println(Colors.LIGHT_BLUE + "Delay time: " + Colors.RESET + currentHomework.getDelayTime());
        switch (MenuOptions.printMenu()) {
            case ADD_QUESTION:
                addQuestion();
                break;
            case QUESTIONS:
                showQuestions();
                break;
//            case SCOREBOARD:
//                showScoreboard();
//                break;
            case ANSWERS:
                showAnswers();
                break;
            case USER_ANSWERS:
                showUserAnswers();
                break;
            case EDIT_HOMEWORK_DETAILS:
                editHomeworkDetails();
                break;
            case DELETE_HOMEWORK:
                deleteHomework();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void addQuestion() {
        System.out.print("Enter question name: ");
        Question question = new Question(ScannerWrapper.nextLine());
        System.out.print("Enter question description: ");
        question.setDescription(ScannerWrapper.nextLine());
        System.out.print("Enter question score: ");
        question.setScore(ScannerWrapper.nextInt());
        System.out.print("Enter question difficulty (1:Easy, 2:Medium, 3:Hard, 4:Very Hard): ");
        question.setLevel(QuestionLevel.values()[ScannerWrapper.nextInt(1, 4) - 1]);
        System.out.print("Enter question type (1:Short ans, 2:Long ans, 3:Multiple choice, 4:Fill in the blank): ");
        question.setType(QuestionType.values()[ScannerWrapper.nextInt(1, 4) - 1]);
        if (question.getType() == QuestionType.MULTIPLE_CHOICE || question.getType() == QuestionType.SHORT_ANSWER) {
            System.out.print("Has this question auto score? (y/n): ");
            question.sethasAutoScore(ScannerWrapper.nextYesNo());
            if (question.hasAutoScore()) {
                System.out.print("Enter question answer: ");
                String answer = ScannerWrapper.nextLine();
                question.setAnswer(answer);
            }
        }
        Errors error = currentHomework.addQuestion(question);
        if (error != Errors.NO_ERROR) {
            System.out.println(error);
            return;
        }
        System.out.println(Colors.GREEN + "Question added successfully!" + Colors.RESET);
    }

    public void showQuestions() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.Questions);
    }

//    public void showScoreboard() {
//        ScoreBoard scoreBoard = new ScoreBoard(currentCourse, currentHomework);
//        System.out.println(scoreBoard.toString());
//        Errors error = scoreBoard.exportHTML();
//        if (error != Errors.NO_ERROR) {
//            System.out.println(error);
//        } else {
//            System.out.println(Colors.GREEN + "HTML exported!" + Colors.RESET);
//        }
//        System.out.print("Press enter to continue ");
//        ScannerWrapper.nextLine();
//    }

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
        ArrayList<Answer> answers = currentHomework.getFinalAnswers(user);
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

    public void editHomeworkDetails() {
        System.out.println("Leave field blank to keep it unchanged");
        System.out.print("name from " + Colors.GRAY + currentHomework.getName() + Colors.RESET + " to: ");
        String name = ScannerWrapper.nextLine();
        name = name.isBlank() ? currentHomework.getName() : name;
        ArrayList<Homework> homeworks = currentCourse.getHomeworks();
        homeworks.remove(currentHomework);
        for (Homework homework : homeworks) {
            if (homework.getName().equals(name)) {
                System.out.println(Errors.DUPLICATE_HOMEWORK);
                return;
            }
        }
        currentHomework.setName(name);
        System.out.print("description from " + Colors.GRAY + currentHomework.getDescription() + Colors.RESET + " to: ");
        String temp = ScannerWrapper.nextLine();
        currentHomework.setDescription(temp.isBlank() ? currentHomework.getDescription() : temp);
        homeworkEditTimes();
        System.out.print("delay rate from " + Colors.GRAY + currentHomework.getDelayRate() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        currentHomework.setDelayRate(temp.isBlank() ? currentHomework.getDelayRate() : Float.parseFloat(temp));
        System.out.print("test mode? was " + currentHomework.isTestMode() + " (y/n): ");
        String isTest = ScannerWrapper.nextLine();
        if (isTest.equals("y")) {
            currentHomework.setTestMode(true);
        } else if (!isTest.isBlank()) {
            currentHomework.setTestMode(false);
        }
        System.out.print("public score board? was " + currentHomework.hasScoreTable() + " (y/n): ");
        String hasScoreTable = ScannerWrapper.nextLine();
        if (hasScoreTable.equals("n")) {
            currentHomework.setHasScoreTable(false);
        } else if (!hasScoreTable.isBlank()) {
            currentHomework.setHasScoreTable(true);
        }
        System.out.println(Colors.GREEN + "Homework edited successfully!" + Colors.RESET);
    }

    private void homeworkEditTimes() {
        System.out.print("start time from " + Colors.GRAY + currentHomework.getStartTime() + Colors.RESET + " to: ");
        String temp = ScannerWrapper.nextLine();
        currentHomework.setStartTime(temp.isBlank() ? currentHomework.getStartTime() : dateTimeFromString(temp));
        System.out.print("end time from " + Colors.GRAY + currentHomework.getEndTime() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        DateTime endTime = temp.isBlank() ? currentHomework.getEndTime() : dateTimeFromString(temp);
        while (endTime.compareTo(currentHomework.getStartTime()) < 0) {
            System.out.println(Errors.END_TIME_BEFORE_START_TIME);
            System.out.print("(yyyy/mm/dd hh:mm): ");
            temp = ScannerWrapper.nextLine();
            endTime = temp.isBlank() ? currentHomework.getEndTime() : dateTimeFromString(temp);
        }
        currentHomework.setEndTime(endTime);
        System.out.print("delay time from " + Colors.GRAY + currentHomework.getDelayTime() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        DateTime delayTime = temp.isBlank() ? currentHomework.getDelayTime() : dateTimeFromString(temp);
        while (delayTime.compareTo(currentHomework.getEndTime()) < 0) {
            System.out.println(Errors.DELAY_TIME_BEFORE_END_TIME);
            System.out.print("(yyyy/mm/dd hh:mm): ");
            temp = ScannerWrapper.nextLine();
            delayTime = temp.isBlank() ? currentHomework.getDelayTime() : dateTimeFromString(temp);
        }
        currentHomework.setDelayTime(delayTime);
    }

    private DateTime dateTimeFromString(String input) {
        String[] parts = input.split("\s");
        DateTime dateTime;
        try {
            dateTime = new DateTime(parts[0], parts[1]);
        } catch (Exception e) {
            System.out.print("(yyyy/mm/dd hh:mm): ");
            dateTime = ScannerWrapper.nextDateTime();
        }
        return dateTime;
    }

    public void deleteHomework() {
        currentCourse.deleteHomework(currentHomework);
        Manager.getInstance().setCurrentHomework(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.HOMEWORKS);
    }

    public void back() {
        Manager.getInstance().setCurrentHomework(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.HOMEWORKS);
    }

    private enum MenuOptions {
        ADD_QUESTION("Add question"),
        QUESTIONS("Questions"),
        SCOREBOARD("Scoreboard"),
        ANSWERS("Final Answers"),
        USER_ANSWERS("Final answers of a user"),
        EDIT_HOMEWORK_DETAILS("Edit homework details"),
        DELETE_HOMEWORK("Delete homework"),
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
