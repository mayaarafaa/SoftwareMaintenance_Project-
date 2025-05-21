package com.lms.learningmanagementsystem;

import com.lms.learningmanagementsystem.model.assessment.Grading;
import com.lms.learningmanagementsystem.model.assessment.Quiz;
import com.lms.learningmanagementsystem.model.Course;
import com.lms.learningmanagementsystem.model.user.Instructor;
import com.lms.learningmanagementsystem.model.user.Student;
import com.lms.learningmanagementsystem.service.AssessmentService;
import com.lms.learningmanagementsystem.service.CourseService;
import com.lms.learningmanagementsystem.service.NotificationService;
import com.lms.learningmanagementsystem.service.userservice.InstructorService;
import com.lms.learningmanagementsystem.service.userservice.StudentService;
import com.lms.learningmanagementsystem.service.userservice.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class InstructorServiceTest {

    private CourseService courseService;
    private NotificationService notificationService;
    private AssessmentService assessmentService;
    private InstructorService instructorService;

    @BeforeEach
    void setUp() {
        courseService = mock(CourseService.class);
        notificationService = mock(NotificationService.class);
        assessmentService = mock(AssessmentService.class);

        // Here we pass mocked services to the InstructorService constructor
        new InstructorService(courseService, notificationService, assessmentService);
    }

    @Test
    void testAssignInstructorToCourse_Success() {
        Long instructorId = 1L;
        String courseId = "course1";
        Instructor instructor = mock(Instructor.class);
        Course course = mock(Course.class);

        // Simulate course service finding a course
        when(courseService.findCourseById(courseId)).thenReturn(course);
        UserService.userStore.put(instructorId, instructor); // Store instructor in userStore

        // Call the method to test
        boolean result = InstructorService.assignInstructorToCourse(instructorId, courseId);

        // Validate the outcome
        assertTrue(result);
        verify(course).setInstructor(instructor);
        verify(notificationService).notifyUser(eq(instructorId), anyString());
    }

    @Test
    void testGenerateOtpForLesson_Success() {
        Long instructorId = 1L;
        String courseId = "course1";
        String lessonId = "lesson1";
        Instructor instructor = mock(Instructor.class);

        // Store the instructor in userStore
        UserService.userStore.put(instructorId, instructor);

        // Simulate OTP generation from course service
        when(courseService.generateOtp(courseId, lessonId)).thenReturn("123456");

        // Call the method to test
        String otp = InstructorService.generateOtpForLesson(instructorId, courseId, lessonId);

        // Validate the outcome
        assertNotNull(otp);
        assertEquals("123456", otp);
    }

    @Test
    void testMarkAttendance_Success() {
        Long studentId = 1L;
        String courseId = "course1";
        String lessonId = "lesson1";
        String otp = "123456";
        Student student = mock(Student.class);

        // Store student in userStore
        UserService.userStore.put(studentId, student);

        // Simulate attendance marking from course service
        when(courseService.markAttendance(courseId, lessonId, studentId.toString(), true)).thenReturn(true);

        // Call the method to test
        boolean result = StudentService.markAttendance(studentId, courseId, lessonId, otp);

        // Validate the outcome
        assertTrue(result);
    }

    @Test
    void testCreateQuiz() {
        Long instructorId = 1L;
        String title = "Quiz1";
        int num = 10;
        int totalMarks = 100;
        Instructor instructor = mock(Instructor.class);
        Quiz quiz = mock(Quiz.class);

        // Mock the method from UserService
        UserService.userStore.put(instructorId, instructor);
        // Mock static method from InstructorService
        when(InstructorService.createQuiz(instructorId, title, num, totalMarks)).thenReturn(quiz);

        // Calling the static method
        Quiz createdQuiz = InstructorService.createQuiz(instructorId, title, num, totalMarks);

        assertNotNull(createdQuiz);
    }

    @Test
    void testTrackStudentPerformance() {
        Long instructorId = 1L;
        Long studentId = 1L;
        List<Grading> gradings = Collections.emptyList();

        Instructor instructor = mock(Instructor.class);
        // Simulate tracking student performance
        UserService.userStore.put(instructorId, instructor);
        when(InstructorService.trackStudentPerformance(instructorId,studentId)).thenReturn(gradings);

        // Call the method to test
        List<Grading> result = InstructorService.trackStudentPerformance(instructorId,studentId);

        // Validate the outcome
        assertEquals(gradings, result);
    }

    
}
