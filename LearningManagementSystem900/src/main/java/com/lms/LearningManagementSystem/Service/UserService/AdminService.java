package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.Course;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends UserService {

    public AdminService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static Course createCourse(Long AdminId,String title, String description, int duration) {
        return courseService.createCourse(AdminId,title, description, duration);
    }

    public static Course updateCourse(Long AdminId,String courseId, String title, String description, int duration) {
        Course course = courseService.findCourseById(courseId);
        return courseService.updateCourse( AdminId,courseId, title, description, duration);
    }

    public static boolean deleteCourse(Long AdminId,String courseId) {
        Course course = courseService.findCourseById(courseId);
        return courseService.deleteCourse(AdminId,courseId);
    }

}
