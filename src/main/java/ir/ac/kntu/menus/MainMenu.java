package ir.ac.kntu.menus;

import ir.ac.kntu.Manager;
import ir.ac.kntu.helpers.Colors;
import ir.ac.kntu.helpers.Errors;
import ir.ac.kntu.helpers.MenuLevel;
import ir.ac.kntu.helpers.ScannerWrapper;
import ir.ac.kntu.models.Course;
import ir.ac.kntu.models.Quera;
import ir.ac.kntu.models.User;

public class MainMenu implements Menu{
    private Quera quera;

    private User currentUser;

    public MainMenu(){
        quera = Manager.getInstance().getQuera();
        currentUser = Manager.getInstance().getCurrentUser();
        printMenu();
    }

    public void printMenu(){
        System.out.println(Colors.LIGHT_BLUE + "Welcome back, " + Colors.ORANGE + currentUser.getName() + Colors.RESET);
        switch (MenuOptions.printMenu()) {
            case NEW_COURSE:
                newCourse();
                break;
            case MY_COURSES:
                myCourses();
                break;
            case SEARCH:
                search();
                break;
            case QUESTION_BANK:
                questionBank();
                break;
            case SIGN_OUT:
                signOut();
                break;
            default:
                break;
        }
    }

    public void newCourse() {
        System.out.print("Enter course name: ");
        String name = ScannerWrapper.nextLine();
        System.out.print("Enter course teacher: ");
        String teacher = ScannerWrapper.nextLine();
        System.out.print("Enter course description: ");
        String description = ScannerWrapper.nextLine();
        Course course = new Course(currentUser, name, teacher, description);
        System.out.print("Enter course institute: ");
        course.setInstitute(ScannerWrapper.nextLine());
        System.out.print("Enter course year: ");
        course.setYear(ScannerWrapper.nextLine());
        System.out.print("is this course private? (y/n): ");
        course.setPrivate(ScannerWrapper.nextYesNo());
        if (course.isPrivate()) {
            course.setPrivate(true);
            System.out.print("Enter course password: ");
            course.setPassword(ScannerWrapper.nextLine());
        }
        System.out.print("is this course open for registration? (y/n): ");
        course.setOpenForRegistration(ScannerWrapper.nextYesNo());
        Errors error = quera.addCourse(course);
        if (error != Errors.NO_ERROR) {
            System.out.println(error);
            return;
        }
        System.out.println(Colors.GREEN + "Course added successfully!" + Colors.RESET);
    }

    public void myCourses() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.COURSES);
    }

    public void search() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.SEARCH);
    }

    public void questionBank() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.QUESTION_BANK);
    }

    public void signOut() {
        Manager.getInstance().setCurrentUser(null);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.WITHOUT_USER);
    }


    private enum MenuOptions {
        NEW_COURSE("New course"),
        MY_COURSES("My courses"),
        SEARCH("Search"),
        QUESTION_BANK("Question bank"),
        SIGN_OUT("Sign out");

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

