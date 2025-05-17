package com.lms.LearningManagementSystem.Service;

import com.lms.LearningManagementSystem.Model.User.Admin;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.Notification;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.Lesson;
import com.lms.LearningManagementSystem.Model.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

import static com.lms.LearningManagementSystem.Service.UserService.UserService.userStore;

@Service
public class CourseService {
    private final AtomicLong idGenerator = new AtomicLong(1);
    private final List<Course> courses = new ArrayList<>();
    private final NotificationService notificationService;

    @Autowired
    public CourseService(@Lazy NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private String generateId() {
        return String.valueOf(idGenerator.getAndIncrement());
    }

    public Course createCourse(Long AdminId,String title, String description, int duration) {
        // Check if the user exists and is an admin
        User user = userStore.get(AdminId);
        if (user == null || !(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        String courseId = generateId();
        Course course = new Course(AdminId,courseId, title, description, duration);
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

    public Course updateCourse(Long AdminId,String courseId, String title, String description, int duration) {
        // Check if the user exists and is an admin
        User user = userStore.get(AdminId);
        if (user == null || !(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        Course course = findCourseById(courseId);
        if (course != null) {
            course.setTitle(title);
            course.setDescription(description);
            course.setDuration(duration);

            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId,
                        "The course " + course.getTitle() + " has been updated. Please check for new details.");
            }
            return course;
        }
        return null;
    }

    public boolean deleteCourse(Long AdminId,String courseId) {
        // Check if the user exists and is an admin
        User user = userStore.get(AdminId);
        if (user == null || !(user instanceof Admin)) {
            throw new IllegalArgumentException("Only admins can create courses.");
        }
        Course course = findCourseById(courseId);
        if (course != null) {
            for (Long studentId : course.getEnrolledStudents()) {
                notificationService.notifyUser(studentId,
                        "The course " + course.getTitle() + " has been deleted.");
            }

            if (course.getInstructor() != null) {
                notificationService.notifyUser(course.getInstructor().getId(),
                        "The course " + course.getTitle() + " you were assigned to teach has been deleted.");
            }

            courses.remove(course);
            return true;
        }
        return false;
    }

}
