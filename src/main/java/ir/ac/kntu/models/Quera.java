package ir.ac.kntu.models;

import ir.ac.kntu.helpers.Errors;

import java.util.ArrayList;

public class Quera {
    private ArrayList<Course> courses;

    private ArrayList<User> users;

    public Quera(){
        this.courses = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public Errors addUser(User user){
        Errors error = isDuplicateUser(user);
        if(error != Errors.NO_ERROR){
            return error;
        }
        users.add(user);
        return Errors.NO_ERROR;
    }

    public Errors addCourse(Course course){
        if (courses.contains(course)){
            return Errors.DUPLICATE_COURSE;
        }
        courses.add(course);
        course.getOwner().addCourse(course);
        return Errors.NO_ERROR;
    }

    public Errors deleteCourse(Course course){
        if (!courses.contains(course)){
            return Errors.NO_ERROR;
        }
        courses.remove(course);
        for (User user : course.getStudents()){
            user.deleteCourse(course);
        }
        return Errors.NO_ERROR;
    }

    public User searchUserByEmail(String email){
        for (User user : users){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    public User searchUserByUsername(String username){
        for (User user : users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User searchUserByNationalCode(int nationalCode){
        for (User user : users){
            if (user.getNationalCode().equals(nationalCode)){
                return user;
            }
        }
        return null;
    }

    public ArrayList<Course> searchCoursesByName(String name) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getName().contains(name)) {
                result.add(course);
            }
        }
        return result;
    }

    public ArrayList<Course> searchCoursesByTeacher(String teacherName) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getTeacherName().contains(teacherName)) {
                result.add(course);
            }
        }
        return result;
    }

    public ArrayList<Course> searchCoursesByInstitute(String institute) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course course : courses) {
            if (course.getInstitute().contains(institute)) {
                result.add(course);
            }
        }
        return result;
    }

    public Errors isDuplicateUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return Errors.DUPLICATE_USERNAME;
            } else if (u.getEmail().equals(user.getEmail())) {
                return Errors.DUPLICATE_EMAIL;
            }
        }
        return Errors.NO_ERROR;
    }
}
