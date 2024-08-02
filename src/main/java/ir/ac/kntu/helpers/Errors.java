package ir.ac.kntu.helpers;

public enum Errors {
    INVALID_INPUT("Invalid input!"),
    DUPLICATE_USERNAME("This username is already taken!"),
    DUPLICATE_EMAIL("This email is already registered!"),
    DUPLICATE_COURSE("This course name is already taken!"),
    DUPLICATE_EVENT("This event name is already taken!"),
    USER_NOT_FOUND("User not found!"),
    WRONG_PASSWORD("Wrong password!"),
    NO_COURSES("No courses found!"),
    NO_EVENT("No event found!"),
    USER_ALREADY_IN_COURSE("User is already in this course!"),
    COURSE_NOT_OPEN("This course is not open for registration!"),
    DUPLICATE_HOMEWORK("This homework already exists!"),
    END_TIME_BEFORE_START_TIME("End time must be after start time!"),
    DELAY_TIME_BEFORE_END_TIME("Delay time must be after end time!"),
    DUPLICATE_QUESTION("This question already exists!"),
    NO_HOMEWORKS("No homework found!"),
    NO_QUESTIONS("No questions found!"),
    ANSWER_ALREADY_SCORED("This answer has already been scored!"),
    SCORE_BOARD_PRIVATE("Score board is not public!"),
    ANSWER_DELAYED("Homework time has passed!"),
    QUESTION_NOT_AUTO_SCORED("This question is not auto scored!"),
    FILE_ERROR("Error saving file!"),
    NO_ERROR("");

    private String message;

    //private Colors errorColor = Colors.RED;

    private Errors(String message) {
        this.message = message;
    }

    public String toString() {
       // return errorColor + message + Colors.RESET;
        return message;
    }
}
