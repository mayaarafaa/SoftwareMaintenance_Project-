package com.lms.LearningManagementSystem.Controller;
import java.util.List;
import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import com.lms.LearningManagementSystem.Service.UserService.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/Assessments")
public class AssessmentController {

    // Create Quiz
    @PostMapping("/{InstructorId}/quiz")
    public ResponseEntity<?> createQuiz(@PathVariable Long InstructorId,@RequestBody Map<String, Object> payload) {
        try {
            String title = (String) payload.get("title");
            int totalMarks = (int) payload.get("totalMarks");
            int num = (int) payload.get("num");
            Quiz quiz = InstructorService.createQuiz(InstructorId, title, num, totalMarks);
            return new ResponseEntity<>(quiz, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Operation failed: You are not an Instructor.", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/quiz/{quizId}/submit")
    public ResponseEntity<?> submitQuizAnswers(@PathVariable Long quizId, @RequestBody Map<String, Object> payload) {
        // Check if the quiz exists
        try {
            Quiz quiz = StudentService.findQuizById(quizId);
            if (quiz == null) {
                return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND); // Return 404 if quiz not found
            }
            Long studentId = ((Number) payload.get("studentId")).longValue();
            Map<String, String> submission = (Map<String, String>) payload.get("answers");

            // Process submission
            StudentService.SubmitQuiz(studentId, quizId, submission);
            int correctAnswersCount = InstructorService.correctAnswersCount(quizId, studentId);
            return new ResponseEntity<>("You got " + correctAnswersCount + " correct answers!", HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            // Handle known errors like invalid student or other validations
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    // Add Question to Quiz
    @PostMapping("/{InstructorId}/create/questions")
    public ResponseEntity<String> addQuestions( @PathVariable Long InstructorId,@RequestBody List<Question> questions) {
        try {
            if (questions == null || questions.isEmpty()) {
                return new ResponseEntity<>("No questions provided!", HttpStatus.BAD_REQUEST);
            }
            InstructorService.addQuestions(InstructorId, questions);
            return new ResponseEntity<>("Questions added successfully!", HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            // Handle known errors like invalid instructor ID
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long id) {
        Quiz quiz = StudentService.findQuizById(id);
        if (quiz == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if no quiz is found
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }
    
    @GetMapping("/{InstructorId}/questions")
    public  ResponseEntity<?> getAllQuestions(@PathVariable Long InstructorId) {
        try {
            // Fetch questions for the instructor
            List<Question> questions = InstructorService.GetQuestions(InstructorId);

            // Return the questions if the operation succeeds
            return new ResponseEntity<>(questions, HttpStatus.OK);

        }
        catch (IllegalArgumentException e) {
            // Handle known errors like invalid instructor ID
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/quizzes")
    public List<Quiz> GetAllquizzes() {
        return StudentService.GetAllquizzes();
    }

    // Create Assignment
    @PostMapping("/{InstructorId}/assignment")
    public ResponseEntity<?> createAssignment(@PathVariable Long InstructorId,@RequestBody Map<String, Object> payload) {
        try {
            // Extract title and description from payload
            String title = (String) payload.get("title");
            String description = (String) payload.get("description");
            if (title == null || title.isEmpty() || description == null || description.isEmpty()) {
                return new ResponseEntity<>("Title and description must be provided.", HttpStatus.BAD_REQUEST);
            }
            Assignment assignment = InstructorService.createAssignment(InstructorId, title, description);

            return new ResponseEntity<>(assignment, HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            // Handle known errors like invalid instructor ID
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    // Submit Assignment
    @PostMapping("/assignment/{assignmentId}/submit")
    public ResponseEntity<?> submitAssignment(@PathVariable Long assignmentId, @RequestBody Map<String, Object> payload) {
        try {
            // Check if the assignment exists
            Assignment assignment = StudentService.findAssignmentById(assignmentId);
            if (assignment == null) {
                return new ResponseEntity<>("Assignment not found", HttpStatus.NOT_FOUND);
            }

            // Extract fileName and studentId from the payload
            String fileName = (String) payload.get("fileName");
            if (fileName == null || fileName.isEmpty()) {
                return new ResponseEntity<>("File name must be provided.", HttpStatus.BAD_REQUEST);
            }

            Long studentId = ((Number) payload.get("StudentID")).longValue();
            if (studentId == null) {
                return new ResponseEntity<>("Student ID must be provided.", HttpStatus.BAD_REQUEST);
            }

            // Submit the assignment
            StudentService.submitAssignment(assignmentId, fileName, studentId);

            return new ResponseEntity<>("Assignment submitted successfully!", HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            // Handle known validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long assignmentId) {
        Assignment assignment = StudentService.findAssignmentById(assignmentId);
        if (assignment == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 if assignment not found
        }
        return new ResponseEntity<>(assignment, HttpStatus.OK);
    }


    @GetMapping("/assignments")
    public List<Assignment> GetAllAssignments() {
        return StudentService.GetAllAssignments();
    }


    // Grade Assessment
    @PostMapping("/{InstructorId}/grade")
    public ResponseEntity<String> gradeAssignment(@PathVariable Long InstructorId,@RequestBody Map<String, Object> payload) {
        try {
            // Extract and validate inputs
            Long studentId = payload.get("studentId") != null ? ((Number) payload.get("studentId")).longValue() : null;
            if (studentId == null) {
                return new ResponseEntity<>("Student ID must be provided.", HttpStatus.BAD_REQUEST);
            }
            String marks = (String) payload.get("marks");
            if (marks == null || marks.isEmpty()) {
                return new ResponseEntity<>("Marks must be provided.", HttpStatus.BAD_REQUEST);
            }
            String feedback = (String) payload.get("feedback");
            if (feedback == null || feedback.isEmpty()) {
                return new ResponseEntity<>("Feedback must be provided.", HttpStatus.BAD_REQUEST);
            }
            // Grade the assignment
            InstructorService.gradeAssignment(InstructorId, studentId, "Assignment", marks, feedback);
            return new ResponseEntity<>("Assignment graded successfully!", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle known validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

 
}
