package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.models.*;
import ir.ac.kntu.helpers.*;

public class OwnedCourseMenu implements Menu {
    private Quera quera;

    private User currentUser;

    private Course currentCourse;

    public OwnedCourseMenu() {
        quera = Manager.getInstance().getQuera();
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.LIGHT_BLUE + "Course " + Colors.ORANGE + currentCourse.getName() + Colors.RESET);
        System.out.println(Colors.LIGHT_BLUE + "Description: " + Colors.RESET + currentCourse.getDescription());
        switch(MenuOptions.printMenu()) {
            case ADD_TO_COURSE:
                addToCourse();
                break;
            case ADD_HOMEWORK:
                addHomework();
                break;
            case HOMEWORKS:
                showHomeworks();
                break;
            case EDIT_COURSE_DETAILS:
                editCourseDetails();
                break;
            case DELETE_COURSE:
                deleteCourse();
                break;
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void addToCourse() {
        System.out.print("Enter student email or username: ");
        String input = ScannerWrapper.nextLine();
        User user = quera.searchUserByEmail(input);
        if (user == null) {
            user = quera.searchUserByUsername(input);
        }
        if (user == null) {
            System.out.println(Errors.USER_NOT_FOUND);
            return;
        }
        Errors error = currentCourse.addStudent(user);
        if (error != Errors.NO_ERROR) {
            System.out.println(error);
            return;
        }
        System.out.println(Colors.GREEN + "Student added successfully!" + Colors.RESET);
    }

    public void addHomework() {
        System.out.print("Enter homework name: ");
        Homework homework = new Homework(ScannerWrapper.nextLine());
        System.out.print("Enter homework description: ");
        homework.setDescription(ScannerWrapper.nextLine());
        System.out.print("Enter homework start time (yyyy/mm/dd hh:mm):");
        homework.setStartTime(ScannerWrapper.nextDateTime());
        System.out.print("Enter homework end time (yyyy/mm/dd hh:mm): ");
        DateTime endTime = ScannerWrapper.nextDateTime();
        while (endTime.compareTo(homework.getStartTime()) < 0) {
            System.out.println(Errors.END_TIME_BEFORE_START_TIME);
            System.out.print("(yyyy/mm/dd hh:mm): ");
            endTime = ScannerWrapper.nextDateTime();
        }
        homework.setEndTime(endTime);
        System.out.print("Enter homework delay time (yyyy/mm/dd hh:mm): ");
        DateTime delayTime = ScannerWrapper.nextDateTime();
        while (delayTime.compareTo(homework.getEndTime()) < 0) {
            System.out.println(Errors.DELAY_TIME_BEFORE_END_TIME);
            System.out.print("(yyyy/mm/dd hh:mm): ");
            delayTime = ScannerWrapper.nextDateTime();
        }
        homework.setDelayTime(delayTime);
        System.out.print("Enter homework delay rate: ");
        homework.setDelayRate(ScannerWrapper.nextFloat());
        System.out.print("is homework in test mode? (y/n): ");
        homework.setTestMode(ScannerWrapper.nextYesNo());
        System.out.print("is homework's score table public? (y/n): ");
        homework.setHasScoreTable(ScannerWrapper.nextYesNo());
        Errors error = currentCourse.addHomework(homework);
        if (error != Errors.NO_ERROR) {
            System.out.println(error);
            return;
        }
        System.out.println(Colors.GREEN + "Homework added successfully!" + Colors.RESET);
    }

    public void showHomeworks() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.HOMEWORKS);
    }

    public void editCourseDetails() {
        System.out.println("Leave field blank to keep it unchanged");
        System.out.print("name from " + Colors.GRAY + currentCourse.getName() + Colors.RESET + " to: ");
        String name = ScannerWrapper.nextLine();
        name = name.isBlank() ? currentCourse.getName() : name;
        ArrayList<Course> courses = currentUser.getCourses();
        courses.remove(currentCourse);
        for (Course course : courses) {
            if (course.getName().equals(name)) {
                System.out.println(Errors.DUPLICATE_COURSE);
                return;
            }
        }
        currentCourse.setName(name);
        System.out.print("teacher from " + Colors.GRAY + currentCourse.getTeacherName() + Colors.RESET + " to: ");
        String temp = ScannerWrapper.nextLine();
        currentCourse.setTeacherName(temp.isBlank() ? currentCourse.getTeacherName() : temp);
        System.out.print("description from " + Colors.GRAY + currentCourse.getDescription() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        currentCourse.setDescription(temp.isBlank() ? currentCourse.getDescription() : temp);
        System.out.print("institute from " + Colors.GRAY + currentCourse.getInstitute() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        currentCourse.setInstitute(temp.isBlank() ? currentCourse.getInstitute() : temp);
        System.out.print("year from " + Colors.GRAY + currentCourse.getYear() + Colors.RESET + " to: ");
        temp = ScannerWrapper.nextLine();
        currentCourse.setYear(temp.isBlank() ? currentCourse.getYear() : temp);
        System.out.print("private? was " + currentCourse.isPrivate() + " (y/n): ");
        String isPrivate = ScannerWrapper.nextLine();
        if (isPrivate.equals("y")) {
            currentCourse.setPrivate(true);
            System.out.print("password from " + Colors.GRAY + currentCourse.getPassword() + Colors.RESET + " to: ");
            temp = ScannerWrapper.nextLine();
            currentCourse.setPassword(temp.isBlank() ? currentCourse.getPassword() : temp);
        } else if (!isPrivate.isBlank()) {
            currentCourse.setPrivate(false);
        }
        System.out.print("open for registration? was " + currentCourse.isOpenForRegistration() + " (y/n): ");
        String isOpenForRegistration = ScannerWrapper.nextLine();
        if (isOpenForRegistration.equals("n")) {
            currentCourse.setOpenForRegistration(false);
        } else if (!isOpenForRegistration.isBlank()) {
            currentCourse.setOpenForRegistration(true);
        }
        System.out.println(Colors.GREEN + "Course edited successfully!" + Colors.RESET);
    }

    public void deleteCourse() {
        quera.deleteCourse(currentCourse);
        Manager.getInstance().setCurrentCourse(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.COURSES);
    }

    public void back() {
        Manager.getInstance().setCurrentCourse(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.COURSES);
    }

    private enum MenuOptions {
        ADD_TO_COURSE("Add to course"),
        ADD_HOMEWORK("Add homework"),
        HOMEWORKS("Homeworks"),
        EDIT_COURSE_DETAILS("Edit course details"),
        DELETE_COURSE("Delete course"),
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
