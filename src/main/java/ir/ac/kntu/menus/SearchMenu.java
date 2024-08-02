package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.helpers.*;
import ir.ac.kntu.models.*;

public class SearchMenu implements Menu {
    private Quera quera;

    private User currentUser;

    private Course currentCourse;

    public SearchMenu() {
        quera = Manager.getInstance().getQuera();
        currentUser = Manager.getInstance().getCurrentUser();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.LIGHT_BLUE + "Search" + Colors.RESET);
        switch (MenuOptions.printMenu()) {
            case SEARCH_COURSE_BY_NAME:
                searchCourseByName();
                break;
            case SEARCH_COURSE_BY_TEACHER:
                searchCourseByTeacher();
                break;
            case SEARCH_COURSE_BY_INSTITUTE:
                searchCourseByInstitute();
                break;
            case SEARCH_USER_BY_EMAIL:
                searchUserByEmail();
                break;
            case SEARCH_USER_BY_NAT_CODE:
                searchUserByNatCode();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void searchCourseByName() {
        System.out.print("Enter course name: ");
        String name = ScannerWrapper.nextLine();
        ArrayList<Course> courses = quera.searchCoursesByName(name);
        if (courses.size() == 0) {
            System.out.println(Errors.NO_COURSES);
            return;
        }
        System.out.println(Colors.LIGHT_BLUE + "Courses with name " + Colors.ORANGE + name + Colors.RESET);
        while (chooseCourses(courses) == Errors.INVALID_INPUT) {
            System.out.println(Errors.INVALID_INPUT);
        }
    }

    public void searchCourseByTeacher() {
        System.out.print("Enter teacher name: ");
        String name = ScannerWrapper.nextLine();
        ArrayList<Course> courses = quera.searchCoursesByTeacher(name);
        if (courses.size() == 0) {
            System.out.println(Errors.NO_COURSES);
            return;
        }
        System.out.println(Colors.LIGHT_BLUE + "Courses with teacher name " + Colors.ORANGE + name + Colors.RESET);
        while (chooseCourses(courses) == Errors.INVALID_INPUT) {
            System.out.println(Errors.INVALID_INPUT);
        }
    }

    public void searchCourseByInstitute() {
        System.out.print("Enter institute name: ");
        String name = ScannerWrapper.nextLine();
        ArrayList<Course> courses = quera.searchCoursesByInstitute(name);
        if (courses.size() == 0) {
            System.out.println(Errors.NO_COURSES);
            return;
        }
        System.out.println(Colors.LIGHT_BLUE + "Courses from institute " + Colors.ORANGE + name + Colors.RESET);
        while (chooseCourses(courses) == Errors.INVALID_INPUT) {
            System.out.println(Errors.INVALID_INPUT);
        }
    }

    public void searchUserByEmail() {
        System.out.print("Enter user email: ");
        String email = ScannerWrapper.nextLine();
        User user = quera.searchUserByEmail(email);
        if (user == null) {
            System.out.println(Errors.USER_NOT_FOUND);
            return;
        }
        System.out.println(Colors.LIGHT_BLUE + "User with email " + Colors.ORANGE + email + Colors.RESET);
        System.out.println(user);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.USER);
    }

    public void searchUserByNatCode() {
        System.out.print("Enter user national code: ");
        String natCode = ScannerWrapper.nextLine();
        User user = quera.searchUserByNationalCode(Integer.parseInt(natCode));
        if (user == null) {
            System.out.println(Errors.USER_NOT_FOUND);
            return;
        }
        System.out.println(Colors.LIGHT_BLUE + "User with national code " + Colors.ORANGE + natCode + Colors.RESET);
        System.out.println(user);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.USER);
    }

    public void back() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.MAIN_MENU);
    }

    public Errors chooseCourses(ArrayList<Course> courses) {
        for (int i = 0; i < courses.size(); i++) {
            System.out.println(i + 1 + " - " + courses.get(i).getName());
        }
        System.out.println(courses.size() + 1 + " - " + Colors.GRAY + "Back" + Colors.RESET);
        int option = ScannerWrapper.nextInt();
        if (option > courses.size() + 1 || option < 1) {
            return Errors.INVALID_INPUT;
        } else if (option == courses.size() + 1) {
            return Errors.NO_ERROR;
        }
        currentCourse = courses.get(option - 1);
        Manager.getInstance().setCurrentCourse(currentCourse);
        if (currentCourse.isOwner(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_COURSE_MENU);
        } else if (currentCourse.isStudent(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_COURSE_MENU);
        } else {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.UNPARTICIPATED_COURSE_MENU);
        }
        return Errors.NO_ERROR;
    }

    private enum MenuOptions {
        SEARCH_COURSE_BY_NAME("Search course by name"),
        SEARCH_COURSE_BY_TEACHER("Search course by teacher"),
        SEARCH_COURSE_BY_INSTITUTE("Search course by institute"),
        SEARCH_USER_BY_EMAIL("Search user by email"),
        SEARCH_USER_BY_NAT_CODE("Search user by national code"),
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
