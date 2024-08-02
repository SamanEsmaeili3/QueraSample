package ir.ac.kntu.models;

import java.util.ArrayList;
import java.util.Objects;

import ir.ac.kntu.helpers.Errors;

public class User {
    private String name;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String nationalCode;

    private ArrayList<Course> courses;

    //private ArrayList<Event> events;

    public User(String name, String username, String password, String email, String phone, String nationalCode) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.nationalCode = nationalCode;
        this.courses = new ArrayList<>();
        //this.events = new ArrayList<>();
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.courses = new ArrayList<>();
        //this.events = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public ArrayList<Course> getCourses() {
        return new ArrayList<>(courses);
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = new ArrayList<>(courses);
    }


    public Errors addCourse(Course course) {
        if (courses.contains(course)) {
            return Errors.DUPLICATE_COURSE;
        }
        this.courses.add(course);
        return Errors.NO_ERROR;
    }

    public Errors deleteCourse(Course course) {
        if (!courses.contains(course)) {
            return Errors.NO_COURSES;
        }
        courses.remove(course);
        return Errors.NO_ERROR;
    }

//    public Errors addEvent(Event event) {
//        if (events.contains(event)) {
//            return Errors.DUPLICATE_EVENT;
//        }
//        this.events.add(event);
//        return Errors.NO_ERROR;
//    }

//    public Errors deleteEvent(Event event) {
//        if (!events.contains(event)) {
//            return Errors.NO_EVENT;
//        }
//        events.remove(event);
//        return Errors.NO_ERROR;
//    }

    @Override
    public int hashCode() {
        return Objects.hash(email, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return email.equals(other.getEmail()) && username.equals(other.getUsername());
    }

    @Override
    public String toString() {
        return name + " [@" + username + ", email: " + email + ", national code: " + nationalCode + "]";
    }
}
