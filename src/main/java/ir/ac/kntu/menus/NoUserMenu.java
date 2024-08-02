package ir.ac.kntu.menus;

import ir.ac.kntu.*;
import ir.ac.kntu.helpers.*;
import ir.ac.kntu.models.*;

public class NoUserMenu implements Menu {
    private Quera quera;

    public NoUserMenu() {
        quera = Manager.getInstance().getQuera();
        printMenu();
    }

    public void printMenu() {
        System.out.println(Colors.LIGHT_BLUE + "Welcome to Quera!" + Colors.RESET);
        switch (MenuOptions.printMenu()) {
            case SIGN_IN:
                signIn();
                break;
            case SIGN_UP:
                signUp();
                break;
            case EXIT:
                exit();
                break;
            default:
                break;
        }
    }

    public void signIn() {
        System.out.print("Enter your email or username: ");
        String input = ScannerWrapper.nextLine();
        User user = quera.searchUserByEmail(input);
        if (user == null) {
            user = quera.searchUserByUsername(input);
        }
        if (user == null) {
            System.out.println(Errors.USER_NOT_FOUND);
            return;
        }
        System.out.print("Enter your password: ");
        String password = ScannerWrapper.nextLine();
        if (!user.getPassword().equals(password)) {
            System.out.println(Errors.WRONG_PASSWORD);
            return;
        }
        System.out.println(Colors.GREEN + "Sign in successful!" + Colors.RESET);
        Manager.getInstance().setCurrentUser(user);
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.MAIN_MENU);
    }

    public void signUp() {
        System.out.print("Enter name: ");
        String name = ScannerWrapper.nextLine();
        System.out.print("Enter email: ");
        String email = ScannerWrapper.nextLine();
        System.out.print("Enter password: ");
        User user = new User(email, ScannerWrapper.nextLine());
        user.setName(name);
        System.out.print("Enter username: ");
        user.setUsername(ScannerWrapper.nextLine());
        System.out.print("Enter phone: ");
        user.setPhone(ScannerWrapper.nextLine());
        System.out.print("Enter national code: ");
        user.setNationalCode(ScannerWrapper.nextLine());
        Errors error = quera.addUser(user);
        if (error != Errors.NO_ERROR) {
            System.out.println(error);
            return;
        }
        System.out.println(Colors.GREEN + "User added successfully!" + Colors.RESET);
    }

    public void exit() {
        Manager.getInstance().setCurrentAccessLevel(MenuLevel.EXIT);
    }

    private enum MenuOptions {
        SIGN_IN("Sign in"),
        SIGN_UP("Sign up"),
        EXIT("Exit");

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
