package com.lms.learningmanagementsystem.service.userservice;

import com.lms.learningmanagementsystem.model.Course;
import com.lms.learningmanagementsystem.service.AssessmentService;
import com.lms.learningmanagementsystem.service.CourseService;
import com.lms.learningmanagementsystem.service.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends UserService {

    public AdminService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        super(courseService, notificationService, assessmentService);
    }

    public static Course createCourse(Long adminId,String title, String description, int duration) {
        return courseService.createCourse(adminId,title, description, duration);
    }

    public static Course updateCourse(Long adminId,String courseId, String title, String description, int duration) {
        //Course course = courseService.findCourseById(courseId); Unused
        return courseService.updateCourse( adminId,courseId, title, description, duration);
    }

    public static boolean deleteCourse(Long adminId,String courseId) {
        //Course course = courseService.findCourseById(courseId); unused
        return courseService.deleteCourse(adminId,courseId);
    }

}
