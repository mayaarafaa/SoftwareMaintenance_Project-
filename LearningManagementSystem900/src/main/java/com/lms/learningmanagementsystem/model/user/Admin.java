package com.lms.learningmanagementsystem.model.user;

import java.util.logging.Logger; //Added

public class Admin extends User {

    //Bug 6
    private static final Logger LOGGER = Logger.getLogger(Admin.class.getName());

    public Admin() {
        this.setRole("Admin"); //There is a problem in UserService.AdminService -> It cant read setters/getters annotations
    }

    public void createUser(User user) {
        LOGGER.info("Created user: " + user.getName());
    }

    public void manageCourses() {
        LOGGER.info("Admin managing courses.");
    }

    public void generateReports() {
        LOGGER.info("Admin generating reports.");
    }
}