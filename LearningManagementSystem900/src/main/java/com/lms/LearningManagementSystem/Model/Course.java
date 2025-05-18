package com.lms.LearningManagementSystem.Model;

import com.lms.LearningManagementSystem.Model.User.Instructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Course {
    private String id;
    private String title;
    private String description;
    private int duration; // in hours
    private List<String> mediaFiles;
    private List<Lesson> lessons = new ArrayList<>();
    private List<Long> enrolledStudents;
    private Instructor instructor; // Link to the instructor

    public Course(Long adminId, String id, String title, String description, int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.mediaFiles = new ArrayList<>();
        this.lessons = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

}



