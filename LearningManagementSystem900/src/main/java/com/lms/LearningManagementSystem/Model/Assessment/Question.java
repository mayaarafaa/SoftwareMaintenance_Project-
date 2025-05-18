package com.lms.LearningManagementSystem.Model.Assessment;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Question {
    private Long id;
    private String text;
    //private String type; // MCQ, True/False, Short Answer
    private List<String> options; // For MCQ
    private String correctAnswer;


    public Question(Long id, String text, List<String> options, String correctAnswer) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
    }

}
