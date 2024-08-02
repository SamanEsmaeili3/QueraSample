package ir.ac.kntu.menus;

import java.util.ArrayList;

import ir.ac.kntu.*;
import ir.ac.kntu.helpers.*;
import ir.ac.kntu.models.*;

public class HomeworksMenu implements Menu{
    private User currentUser;

    private Course currentCourse;

    private Homework currentHomework;

    public HomeworksMenu() {
        currentUser = Manager.getInstance().getCurrentUser();
        currentCourse = Manager.getInstance().getCurrentCourse();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.ORANGE + currentCourse.getName() + Colors.LIGHT_BLUE + "'s Homeworks" + Colors.RESET);
        ArrayList<Homework> homeworks = currentCourse.getHomeworks();
        if (!currentCourse.isOwner(currentUser)) {
            deleteUnviewables(homeworks);
        }
        for (int i = 0; i < homeworks.size(); i++) {
            System.out.println(i + 1 + " - " + homeworks.get(i));
        }
        System.out.println(homeworks.size() + 1 + " - " + "Back");
        int option = ScannerWrapper.nextInt();
        if (option > homeworks.size() + 1 || option < 1) {
            System.out.println(Errors.INVALID_INPUT);
            printMenu();
            return;
        } else if (option == homeworks.size() + 1) {
            if (currentCourse.isOwner(currentUser)) {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_COURSE_MENU);
            } else {
                Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_COURSE_MENU);
            }
            return;
        }
        currentHomework = homeworks.get(option - 1);
        Manager.getInstance().setCurrentHomework(currentHomework);
        if (currentCourse.isOwner(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_HOMEWORK_MENU);
        } else {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_HOMEWORK_MENU);
        }
    }

    private void deleteUnviewables(ArrayList<Homework> homeworks) {
        for (int i = 0; i < homeworks.size(); i++) {
            if (!homeworks.get(i).isViewable()) {
                homeworks.remove(i);
                i--;
            }
        }
    }
}
