package com.lms.LearningManagementSystem.Model.Assessment;

import lombok.Getter; //I downloaded Lombak Plugins
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;

@Setter
@Getter
public class Assignment {
    private Long id;
    private String title;
    private String description;
    private int marks;      // Marks given after grading
    private String feedback;
    private List<Map.Entry<Long, String>> submittedFiles = new ArrayList<>(); // File names/paths

    public Assignment(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public void submitFile(Long stringID, String fileName) {
        this.submittedFiles.add(new SimpleEntry<>(stringID, fileName));
    }

}
