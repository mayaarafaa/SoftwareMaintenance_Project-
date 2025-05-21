package com.lms.learningmanagementsystem.service;

import com.lms.learningmanagementsystem.model.user.Admin;

import com.lms.learningmanagementsystem.model.Course;
import com.lms.learningmanagementsystem.model.Lesson;
import com.lms.learningmanagementsystem.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import com.lms.learningmanagementsystem.service.userservice.UserService;

@Service
public class CourseService {

    //Added
    private final UserService userService;

    //Bug4
    private static final String COURSE_PREFIX = "The course";

    private final AtomicLong idGenerator = new AtomicLong(1);
    private final List<Course> courses = new ArrayList<>();
    private final NotificationService notificationService;

    @Autowired
    //Added parameter
    public CourseService(@Lazy NotificationService notificationService, @Lazy UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService; //Added
    }

    private String generateId() {
        return String.valueOf(idGenerator.getAndIncrement());
    }

    public Course createCourse(Long adminId,String title, String description, int duration) {
        // Check if the user exists and is an admin
        User user = userService.getUserById(adminId);
        if (!(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        String courseId = generateId();
        Course course = new Course(courseId, title, description, duration);
        courses.add(course);
        return course;
    }

    public Course findCourseById(String courseId) {
        return courses.stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst()
                .orElse(null);
    }

    public boolean addMediaFile(String courseId, String mediaFile) {
        Course course = findCourseById(courseId);
        if (course != null) {
            course.getMediaFiles().add(mediaFile);
            return true;
        }
        return false;
    }

    public Lesson addLesson(String courseId, String title, String content) {
        Course course = findCourseById(courseId);
        if (course != null) {
            String lessonId = generateId();
            Lesson lesson = new Lesson(lessonId, title, content);
            course.getLessons().add(lesson);
            return lesson;
        }
        return null;
    }

    public String generateOtp(String courseId, String lessonId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    String otp = UUID.randomUUID().toString().substring(0, 6);
                    lesson.setOtp(otp);
                    return otp;
                }
            }
        }
        return null;
    }

    public boolean markAttendance(String courseId, String lessonId, String studentId, boolean present) {
        Course course = findCourseById(courseId);
        if (course != null && course.getEnrolledStudents().contains(Long.valueOf(studentId))) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    lesson.markAttendance(studentId, present);
                    return true;
                }
            }
        }
        return false;
    }

    public Map<String, Boolean> getLessonAttendance(String courseId, String lessonId) {
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Lesson lesson : course.getLessons()) {
                if (lesson.getId().equals(lessonId)) {
                    return lesson.getAttendance();
                }
            }
        }
        return null;
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public List<Long> getEnrolledStudents(String courseId) {
        Course course = findCourseById(courseId);
        return course != null ? course.getEnrolledStudents() : null;
    }

    public Course updateCourse(Long adminId, String courseId, String title, String description, int duration) {
        // Check if the user exists and is an admin
        User user = userService.getUserById(adminId);
        if (user == null || !(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        Course course = findCourseById(courseId);
        if (course != null) {
            course.setTitle(title);
            course.setDescription(description);
            course.setDuration(duration);

            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId, COURSE_PREFIX + course.getTitle() + " has been updated. Please check for new details.");
            }
            return course;
        }
        return null;
    }

    public boolean deleteCourse(Long adminId,String courseId) {
        // Check if the user exists and is an admin
        User user = userService.getUserById(adminId);
        if (user == null || !(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId,
                        COURSE_PREFIX + course.getTitle() + " has been deleted.");
            }

            if (course.getInstructor() != null) {
                notificationService.notifyUser(course.getInstructor().getId(),
                        COURSE_PREFIX + course.getTitle() + " you were assigned to teach has been deleted.");
            }

            courses.remove(course);
            return true;
        }
        return false;
    }

}
