package com.lms.LearningManagementSystem.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {
    private String id;
    private String message;
    private boolean read;

    public Notification(String id, String message) {
        this.id = id;
        this.message = message;
        this.read = false;
    }

    public void markAsRead() {
        this.read = true;
    }

}
