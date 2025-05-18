package com.lms.LearningManagementSystem.Model.User;

import com.lms.LearningManagementSystem.Model.Notification;

import lombok.Setter;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;

    @Getter
    private List<Notification> notifications = new ArrayList<>();

    // Common methods for all users
    public void viewProfile() {
        System.out.println("Viewing profile of: " + this.name);
    }

    public void updateProfile(String newName, String newEmail) {
        this.name = newName;
        this.email = newEmail;
        System.out.println("Profile updated: " + this.name);
    }

    public boolean equals(String role, String role1) {
        if (role.equals(role1)) {
            return true;
        }
        return false;
    }

    public void addNotification(Notification notification) {
        this.notifications.add(notification);
    }

}
