package ir.ac.kntu.menus;

import ir.ac.kntu.*;
import ir.ac.kntu.helpers.*;

public class UserMenu implements Menu {
    public UserMenu() {
        printMenu();
    }

    public void printMenu() {
        switch(MenuOptions.printMenu()) {
            case BACK:
                back();
                break;
            default:
                break;
        }
    }

    public void back() {
        Manager.getInstance().setCurrentAccessLevel(Manager.getInstance().getLastAccessLevel());
    }

    private enum MenuOptions {
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
