package ir.ac.kntu.helpers;

import java.util.Scanner;

public class ScannerWrapper {
    private static Scanner scanner = new Scanner(System.in);

    private ScannerWrapper() {
    }

    public static int nextInt() {
        int ans = scanner.nextInt();
        scanner.nextLine();
        return ans;
    }

    public static int nextInt(int min, int max) {
        int ans = scanner.nextInt();
        scanner.nextLine();
        while (ans < min || ans > max) {
            System.out.print("(" + min + "-" + max + "): ");
            ans = scanner.nextInt();
            scanner.nextLine();
        }
        return ans;
    }

    public static String nextLine() {
        return scanner.nextLine();
    }

    public static float nextFloat() {
        float ans = scanner.nextFloat();
        scanner.nextLine();
        return ans;
    }

    public static boolean nextYesNo() {
        String ans = scanner.nextLine();
        while (!ans.equals("y") && !ans.equals("n")) {
            System.out.print("(y/n): ");
            ans = scanner.nextLine();
        }
        return ans.equals("y");
    }

    public static DateTime nextDateTime() {
        String input = scanner.nextLine();
        String[] parts = input.split("\s");
        DateTime dateTime;
        while (true) {
            try {
                dateTime = new DateTime(parts[0], parts[1]);
                break;
            } catch (Exception e) {
                System.out.print("(yyyy/mm/dd hh:mm): ");
                input = scanner.nextLine();
                parts = input.split("\s");
            }
        }
        return dateTime;
    }
}
