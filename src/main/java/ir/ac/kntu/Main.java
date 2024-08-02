package ir.ac.kntu;

import ir.ac.kntu.helpers.Colors;

public class Main {
    public static void main(String[] args) {
        Colors.setIsColored(true);
        Manager.getInstance().start();

    }
}
