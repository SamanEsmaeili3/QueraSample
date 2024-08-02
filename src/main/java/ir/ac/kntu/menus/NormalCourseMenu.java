package ir.ac.kntu.menus;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class NormalCourseMenu implements Menu {
    private Course currentCourse;

    public NormalCourseMenu() {
        currentCourse = Manager.getInstance().getCurrentCourse();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.LIGHT_BLUE + "Course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Description: " + Colors.RESET + currentCourse.getDescription());
        switch (MenuOptions.printMenu()) {
            case HOMEWORKS:
                showHomeworks();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void showHomeworks() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.HOMEWORKS);
    }

    public void back() {
        Manager.getInstance().setCurrentCourse(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.COURSES);
    }

    private enum MenuOptions {
        HOMEWORKS("Homeworks"),
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
