package com.lms.learningmanagementsystem.controller;

import com.lms.learningmanagementsystem.model.Notification;
import com.lms.learningmanagementsystem.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{userId}")
    public List<Notification> getNotifications(@PathVariable Long userId, @RequestParam(defaultValue = "false") boolean onlyUnread) {
        return notificationService.getNotifications(userId, onlyUnread);
    }

    @PostMapping("/{userId}/{notificationId}/read")
    public String markNotificationAsRead(@PathVariable Long userId, @PathVariable String notificationId) {
        notificationService.markNotificationAsRead(userId, notificationId);
        return "Notification marked as read.";
    }
}

