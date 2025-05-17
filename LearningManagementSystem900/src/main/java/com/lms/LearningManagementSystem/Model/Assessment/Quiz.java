package com.lms.LearningManagementSystem.Model.Assessment;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Quiz {
    private Long id;
    private String title;

    private List<Question> questions = new ArrayList<>();
    private int totalMarks;

    public Quiz(Long id, String title, int totalMarks, List<Question> selectedQuestions) {
        this.id = id;
        this.questions = selectedQuestions;
        this.title = title;
        this.totalMarks = totalMarks;
    }

    // Add question to quiz
    public void addQuestion(Question question) {
        this.questions.add(question);
    }

}
   
