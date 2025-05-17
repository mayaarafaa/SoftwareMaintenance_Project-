package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.Assessment.Assignment;
import com.lms.LearningManagementSystem.Model.Assessment.Grading;
import com.lms.LearningManagementSystem.Model.Assessment.Question;
import com.lms.LearningManagementSystem.Model.Assessment.Quiz;
import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Model.User.Instructor;
import com.lms.LearningManagementSystem.Model.User.Student;
import com.lms.LearningManagementSystem.Model.User.User;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService extends UserService {

    public InstructorService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static boolean assignInstructorToCourse(Long instructorId, String courseId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor and cannot assign courses.");
        }

        Course course = courseService.findCourseById(courseId);
        if (course == null) {
            return false; // Course not found
        }

        // Assign the instructor to the course
        course.setInstructor((Instructor) user);


        // Notify the instructor about the assignment
        notificationService.notifyUser(instructorId,
                "You have been assigned to teach the course: " + course.getTitle());

        // Notify students about the new instructor
        for (Long studentId : course.getEnrolledStudents()) {
            notificationService.notifyUser(studentId,
                    "The course " + course.getTitle() + " now has a new instructor: " + user.getName());
        }

        return true;
    }

    public static String generateOtpForLesson(Long instructorId, String courseId, String lessonId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
            return courseService.generateOtp(courseId, lessonId);
    }

    public static Quiz createQuiz(Long instructorId,String title, int num, int totalMarks) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        return assessmentService.createQuiz(title, num, totalMarks);
    }

    public static int correctAnswersCount(Long quizId, Long studentId) {
        return assessmentService.correctAnswersCount(quizId, studentId);
    }

    public static void addQuestions(Long instructorId,List<Question> questions) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        assessmentService.addQuestions(questions); }

     public static List<Question> GetQuestions(Long instructorId) {
         User user = userStore.get(instructorId);
         if (user == null || !(user instanceof Instructor)) {
             throw new IllegalArgumentException("User is not an instructor .");
         }

        return assessmentService.GetQuestions();
    }

    public static Assignment createAssignment(Long instructorId,String title, String description) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        return assessmentService.createAssignment(title, description);
    }
    public static void gradeAssignment(Long instructorId,Long studentId, String type, String marks, String feedback) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        assessmentService.gradeAssignment(studentId, type, marks, feedback);
    }

    // Get Gradings
    public static List<Grading> trackStudentPerformance(Long instructorId,Long studentId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        return assessmentService.trackStudentPerformance(studentId);
    }

    public static List<Grading> trackStudentAssignments(Long instructorId,Long studentId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        return assessmentService.trackStudentAssignments(studentId);
    }

    public static List<Grading> trackStudentQuizPerformance(Long instructorId,Long studentId) {
        User user = userStore.get(instructorId);
        if (user == null || !(user instanceof Instructor)) {
            throw new IllegalArgumentException("User is not an instructor .");
        }
        return assessmentService.trackStudentQuizPerformance(studentId);
    }

}
