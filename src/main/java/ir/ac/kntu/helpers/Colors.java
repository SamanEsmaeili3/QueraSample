package ir.ac.kntu.helpers;

public enum Colors {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[1;31;40m"),
    GREEN("\u001B[1;32;40m"),
    YELLOW("\u001B[1;33;40m"),
    BLUE("\u001B[1;34;40m"),
    PURPLE("\u001B[1;35;40m"),
    CYAN("\033[38;2;0;255;255m"),
    ORANGE("\033[38;2;255;215;0m"),
    LIGHT_BLUE("\033[38;2;157;211;255m"),
    WHITE("\033[38;2;255;255;255m"),
    GRAY("\033[38;2;180;180;180m"),
    BOLD_WHITE("\u001B[1;37;40m");

    private String color;

    private static boolean isColored = true;

    Colors(String color) {
        this.color = color;
    }

    public static void setIsColored(boolean isColored) {
        Colors.isColored = isColored;
    }

    public static boolean getIsColored() {
        return Colors.isColored;
    }

    public String toString() {
        return isColored ? color : "";
    }
}
