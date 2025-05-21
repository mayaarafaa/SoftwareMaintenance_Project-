package com.lms.learningmanagementsystem.model.assessment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Quiz {
    private Long id;
    private String title;

    private List<Question> questions = new ArrayList<>();
    private int totalMarks;

    // Add the creationDate field
    private LocalDateTime creationDate;


    public Quiz(Long id, String title, int totalMarks, List<Question> selectedQuestions) {
        this.id = id;
        this.questions = selectedQuestions;
        this.title = title;
        this.totalMarks = totalMarks;
        this.creationDate = LocalDateTime.now(); // Automatically set the creation date

    }

    // Add question to quiz
    public void addQuestion(Question question) {
        this.questions.add(question);
    }



}
   
