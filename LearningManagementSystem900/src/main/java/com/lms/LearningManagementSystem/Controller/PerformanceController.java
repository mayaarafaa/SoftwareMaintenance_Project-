package com.lms.LearningManagementSystem.Controller;

import com.lms.LearningManagementSystem.Model.Assessment.*;
import com.lms.LearningManagementSystem.Service.UserService.InstructorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @GetMapping("/{InstructorId}/track/{studentId}")
        public ResponseEntity<?> trackStudentPerformance(@PathVariable Long InstructorId,@PathVariable Long studentId) {
        try {
            // Fetch student performance data
            List<Grading> performance = InstructorService.trackStudentPerformance(InstructorId, studentId);

            // Check if data is available
            if (performance == null || performance.isEmpty()) {
                return new ResponseEntity<>("No performance data found for the given student.", HttpStatus.NOT_FOUND);
            }

            // Return the performance data
            return new ResponseEntity<>(performance, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/{InstructorId}/assignments/{studentId}")
    public ResponseEntity<?> trackStudentAssignments(@PathVariable Long InstructorId,@PathVariable Long studentId) {
        try {
            // Fetch assignment grading data
            List<Grading> assignments = InstructorService.trackStudentAssignments(InstructorId, studentId);

            // Check if data is available
            if (assignments == null || assignments.isEmpty()) {
                return new ResponseEntity<>("No assignments found for the given student.", HttpStatus.NOT_FOUND);
            }

            // Return the assignment data
            return new ResponseEntity<>(assignments, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }


    @GetMapping("/{InstructorId}/quiz/{studentId}")
    public ResponseEntity<?> trackStudentQuizPerformance(@PathVariable Long InstructorId,@PathVariable Long studentId) {
        try {
            // Fetch quiz performance data
            List<Grading> quizPerformance = InstructorService.trackStudentQuizPerformance(InstructorId, studentId);

            // Validate the response
            if (quizPerformance == null || quizPerformance.isEmpty()) {
                return new ResponseEntity<>("No quiz performance found for the given student.", HttpStatus.NOT_FOUND);
            }
            // Return the quiz performance data
            return new ResponseEntity<>(quizPerformance, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Handle validation errors
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }

}
