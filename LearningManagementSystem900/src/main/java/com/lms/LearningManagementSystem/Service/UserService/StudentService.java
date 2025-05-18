package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.Assessment.Assignment;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.Admin;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.User.Student;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentService extends UserService {
    public StudentService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static boolean enrollInCourse(Long StudentId, String courseId) {
        User user = userStore.get(StudentId);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Only Student can create courses.");
        }
        Course course = courseService.findCourseById(courseId);
        if (course != null && !course.getEnrolledStudents().contains(StudentId)) {
            // Add student to course
            course.getEnrolledStudents().add(Long.parseLong(String.valueOf(StudentId)));
            // Notify student
            notificationService.notifyUser(StudentId,
                    "You have been enrolled in the course: " + course.getTitle());
            // Notify instructor
            if (course.getInstructor() != null) {
                notificationService.notifyUser(course.getInstructor().getId(),
                        "A new student has enrolled in your course: " + course.getTitle());
            }
            return true;
        }
        return false;
    }
    public static boolean markAttendance(Long studentId, String courseId, String lessonId, String otp) {
        User user = userStore.get(studentId);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("Only Student can markAttendance.");
        }
        return courseService.markAttendance(courseId, lessonId, String.valueOf(studentId), true);
    }

    public static void SubmitQuiz(Long studentId,Long quizId, Map<String, String> answers) {
        User user = userStore.get(studentId);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("User is not a Student  .");
        }
        assessmentService.SubmitQuiz(quizId, answers);
    }

    public static void submitAssignment(Long assignmentId, String fileName, Long studID) {
        User user = userStore.get(studID);
        if (user == null || !(user instanceof Student)) {
            throw new IllegalArgumentException("User is not a Student  .");
        }
        assessmentService.submitAssignment(assignmentId, fileName, studID);
    }

    // Get Assignment by ID
    public static Assignment findAssignmentById(Long id) {
        return assessmentService.findAssignmentById(id);
    }

    public static List<Assignment> GetAllAssignments() {
        return assessmentService.GetAllAssignments();
    }

    // Get Quiz by ID
    public static Quiz findQuizById(Long id) {
        return assessmentService.findQuizById(id);
    }

    public static List<Quiz> GetAllquizzes() {
        return assessmentService.GetAllquizzes();
    }

}
