package com.lms.learningmanagementsystem.controller;

import com.lms.learningmanagementsystem.model.Course;
import com.lms.learningmanagementsystem.model.Lesson;

import com.lms.learningmanagementsystem.service.CourseService;
import com.lms.learningmanagementsystem.service.userservice.AdminService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courses")
public class CourseController {

    //Bug 3
    private static final String NOT_ADMIN_MESSAGE = "Operation failed: You are not an admin.";


    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create a course
    @PostMapping("/{adminId}/create")
    public ResponseEntity<?> createCourse(
            @PathVariable Long adminId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration)
    {
        try {
            Course course = AdminService.createCourse(adminId, title, description, duration);
            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(NOT_ADMIN_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    // Update a course
    @PutMapping("/{adminId}/{courseId}/update")
    public ResponseEntity<?> updateCourse(
            @PathVariable Long adminId,
            @PathVariable String courseId,
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam int duration) {
        try {
            Course updatedCourse = AdminService.updateCourse(adminId ,courseId, title, description, duration);
            return ResponseEntity.ok(updatedCourse);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(NOT_ADMIN_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }
    // Delete a course
    @DeleteMapping("/{adminId}/{courseId}/delete")
    public ResponseEntity<String> deleteCourse(@PathVariable String courseId,@PathVariable Long adminId) {
        try {
            boolean isDeleted = AdminService.deleteCourse(adminId,courseId);
            if (isDeleted) {
                return ResponseEntity.ok("Course deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found.");
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(NOT_ADMIN_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    // Add media to a course
    @PostMapping("/{courseId}/media")
    public String addMediaFile(@PathVariable String courseId, @RequestParam String mediaFile) {
        boolean success = courseService.addMediaFile(courseId, mediaFile);
        return success ? "Media file added successfully." : "Failed to add media file.";
    }

    // Add a lesson to a course
    @PostMapping("/{courseId}/lessons")
    public Lesson addLesson(@PathVariable String courseId, @RequestParam String title, @RequestParam String content) {
        return courseService.addLesson(courseId, title, content);
    }

    // View attendance for a lesson
    @GetMapping("/{courseId}/lessons/{lessonId}/attendance")
    public Map<String, Boolean> getLessonAttendance(@PathVariable String courseId, @PathVariable String lessonId) {
        return courseService.getLessonAttendance(courseId, lessonId);
    }

    // View all courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // View enrolled students
    @GetMapping("/{courseId}/students")
    public List<Long> getEnrolledStudents(@PathVariable String courseId) {
        return courseService.getEnrolledStudents(courseId);
    }
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable String courseId) {
        Course course = courseService.findCourseById(courseId);
        if (course != null) {
            return course; // This will include the lessons because they're part of the course object
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
    }

}
