package ir.ac.kntu.menus;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class NormalHomeworkMenu implements Menu {
    private Course currentCourse;

    private Homework currentHomework;

    public NormalHomeworkMenu() {
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
        switch(MenuOptions.printMenu()) {
            case QUESTIONS:
                showQuestions();
                break;
//            case SCOREBOARD:
//                showScoreBoard();
//                break;
            case My_ANSWERS:
                showAnswers();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void showQuestions() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.Questions);
    }

//    public void showScoreBoard() {
//        if (currentHomework.hasScoreTable()) {
//            ScoreBoard scoreBoard = new ScoreBoard(currentCourse, currentHomework);
//            System.out.println(scoreBoard.toString());
//            Errors error = scoreBoard.exportHTML();
//            if (error != Errors.NO_ERROR) {
//                System.out.println(error);
//            } else {
//                System.out.println(Colors.GREEN + "HTML exported!" + Colors.RESET);
//            }
//            System.out.print("Press enter to continue ");
//            ScannerWrapper.nextLine();
//        } else {
//            System.out.println(Errors.SCORE_BOARD_PRIVATE);
//        }
//    }

    public void showAnswers() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.ANSWERS);
    }

    public void back() {
        Manager.getInstance().setCurrentHomework(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.HOMEWORKS);
    }

    private enum MenuOptions {
        QUESTIONS("Questions"),
        SCOREBOARD("Scoreboard"),
        My_ANSWERS("My answers"),
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
