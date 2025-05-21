package com.lms.learningmanagementsystem.model.user;

public class Instructor extends User {

    public Instructor() {
        this.setRole("Instructor");
    }

    public void createCourse(String courseName) {
        System.out.println("Created course: " + courseName);
    }

    public void addContent(String content) {
        System.out.println("Added content: " + content);
    }

    public void gradeAssignments() {
        System.out.println("Instructor grading assignments.");
    }

    public void removeStudentFromCourse(String studentName) {
        System.out.println("Removed student: " + studentName + " from the course.");
    }
}