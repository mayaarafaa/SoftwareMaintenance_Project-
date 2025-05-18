package com.lms.LearningManagementSystem.Model.Assessment;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Grading {
    private Long id;
    private Long studentId;
    private String type;   // "Quiz" or "Assignment"
    private String marks;
    private String feedback;

    public Grading(Long id, Long studentId, String type, String marks, String feedback) {
        this.id = id;
        this.studentId = studentId;
        this.type = type;
        this.marks = marks;
        this.feedback = feedback;
    }

    public Grading(Long id, Long studentId, String marks) {
        this.id = id;
        this.studentId = studentId;
        this.marks = marks;
    }

}
