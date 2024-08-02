package ir.ac.kntu.models;

import java.util.ArrayList;
import java.util.Objects;

import ir.ac.kntu.helpers.Errors;

public class Course {
    private User owner;

    private String name;

    private String institute;

    private String teacherName;

    private String year;

    private String description;

    private String password;

    private boolean isPrivate;

    private boolean openForRegistration;

    private ArrayList<User> students;

    private ArrayList<Homework> homeworks;

    public Course(User owner, String name, String institute, String teacherName, String year, String description, String password,
                  boolean isPrivate, boolean openForRegistration) {
        this.owner = owner;
        this.name = name;
        this.institute = institute;
        this.teacherName = teacherName;
        this.year = year;
        this.description = description;
        this.password = password;
        this.isPrivate = isPrivate;
        this.openForRegistration = openForRegistration;
        this.students = new ArrayList<>();
        this.homeworks = new ArrayList<>();
        this.students.add(owner);
    }

    public Course(User owner, String name, String teacherName, String description) {
        this.owner = owner;
        this.name = name;
        this.teacherName = teacherName;
        this.description = description;
        this.password = "";
        this.isPrivate = false;
        this.openForRegistration = true;
        this.students = new ArrayList<>();
        this.homeworks = new ArrayList<>();
        this.students.add(owner);
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public boolean isOpenForRegistration() {
        return openForRegistration;
    }

    public void setOpenForRegistration(boolean openForRegistration) {
        this.openForRegistration = openForRegistration;
    }

    public ArrayList<User> getStudents() {
        return new ArrayList<>(students);
    }

    public void setStudents(ArrayList<User> students) {
        this.students = students;
    }

    public ArrayList<Homework> getHomeworks() {
        return new ArrayList<>(homeworks);
    }

    public void setHomeworks(ArrayList<Homework> homeworks) {
        this.homeworks = homeworks;
    }

    public boolean isOwner(User user) {
        return this.owner.equals(user);
    }

    public boolean isStudent(User user) {
        return this.students.contains(user);
    }

    public Errors addStudent(User student) {
        if (this.students.contains(student)) {
            return Errors.USER_ALREADY_IN_COURSE;
        }
        this.students.add(student);
        student.addCourse(this);
        return Errors.NO_ERROR;
    }

    public Errors addHomework(Homework homework) {
        if (this.homeworks.contains(homework)) {
            return Errors.DUPLICATE_HOMEWORK;
        }
        this.homeworks.add(homework);
        return Errors.NO_ERROR;
    }

    public Errors deleteHomework(Homework homework) {
        if (!homeworks.contains(homework)) {
            return Errors.NO_HOMEWORKS;
        }
        homeworks.remove(homework);
        return Errors.NO_ERROR;
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Course other = (Course) obj;
        return name.equals(other.name) && owner.equals(other.owner);
    }

    @Override
    public String toString() {
        return name + " [" + "teacher: " + teacherName + ", year: " + year + ", institute: " + institute
                + (isPrivate ? ", Private" : "") + (openForRegistration ? "" : ", Closed") + "]";
    }
}
