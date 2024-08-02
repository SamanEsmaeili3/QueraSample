package ir.ac.kntu.menus;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class UnparticipatedCourseMenu implements Menu {
    private User currentUser;

    private Course currentCourse;

    public UnparticipatedCourseMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.LIGHT_BLUE + "Course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Description: " + Colors.RESET + currentCourse.getDescription());
        switch(MenuOptions.printMenu()) {
            case REGISTER:
                register();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void register() {
        if (!currentCourse.isOpenForRegistration()) {
            System.out.println(Errors.COURSE_NOT_OPEN);
            return;
        }
        if (currentCourse.isPrivate()) {
            System.out.print("Enter password: ");
            String password = ScannerWrapper.nextLine();
            if (!currentCourse.getPassword().equals(password)) {
                System.out.println(Errors.WRONG_PASSWORD);
                return;
            }
        }
        currentCourse.addStudent(currentUser);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_COURSE_MENU);
        System.out.println(Colors.GREEN + "Registration successful!" + Colors.RESET);
    }

    public void back() {
        Manager.getInstance().setCurrentCourse(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.SEARCH);
    }

    private enum MenuOptions {
        REGISTER("Register"),
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
