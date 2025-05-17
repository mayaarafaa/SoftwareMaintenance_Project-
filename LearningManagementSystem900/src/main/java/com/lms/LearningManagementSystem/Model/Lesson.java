package com.lms.LearningManagementSystem.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class Lesson {
    private String id;
    private String title;
    private String content;
    private String otp;
    private Map<String, Boolean> attendance; // Tracks student attendance

    public Lesson(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.attendance = new HashMap<>();
    }

    public void markAttendance(String studentId, boolean present) {
        attendance.put(studentId, present);
    }
}
