package ir.ac.kntu.menus;

import ir.ac.kntu.Manager;
import ir.ac.kntu.helpers.Colors;
import ir.ac.kntu.helpers.Errors;
import ir.ac.kntu.helpers.MenuLevel;
import ir.ac.kntu.helpers.ScannerWrapper;
import ir.ac.kntu.models.Course;
import ir.ac.kntu.models.User;

import java.util.ArrayList;

public class CoursesMenu implements Menu {
    private User currentUser;

    private Course currentCourse;

    public CoursesMenu(){
        currentUser = Manager.getInstance().getCurrentUser();
        printMenu();
    }

    public void printMenu(){
        System.out.println(Colors.ORANGE + currentUser.getName() + Colors.LIGHT_BLUE + "'s Courses" + Colors.RESET);
        ArrayList<Course> courses = currentUser.getCourses();
        for(int i = 0; i<courses.size(); i++){
            System.out.println(i+1 + " - " + courses.get(i));
        }
        System.out.println(courses.size() + 1 + " - " + "Back" );
        int option = ScannerWrapper.nextInt();
        if(option > courses.size() + 1 || option < 1){
            System.out.println(Errors.INVALID_INPUT);
            printMenu();
            return;
        } else if (option == courses.size() + 1) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.MAIN_MENU);
            return;
        }
        currentCourse = courses.get(option - 1);
        Manager.getInstance().setCurrentCourse(currentCourse);
        if(currentCourse.isOwner(currentUser)){
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.OWNED_COURSE_MENU);
        } else if (currentCourse.isStudent(currentUser)) {
            Manager.getInstance().setCurrentAccessLevel(MenuLevel.NORMAL_COURSE_MENU);
        } else {
          Manager.getInstance().setCurrentAccessLevel(MenuLevel.UNPARTICIPATED_COURSE_MENU);
        }
    }
}
