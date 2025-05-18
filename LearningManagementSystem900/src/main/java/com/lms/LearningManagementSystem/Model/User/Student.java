package com.lms.LearningManagementSystem.Model.User;

public class Student extends User {

    public Student() {
        this.setRole("Student");
    }

    public void enrollInCourse(String courseName) {
        System.out.println("Enrolled in course: " + courseName);
    }

    public void viewCourseMaterials() {
        System.out.println("Viewing course materials.");
    }

    public void submitAssignment(String assignmentName) {
        System.out.println("Submitted assignment: " + assignmentName);
    }
}