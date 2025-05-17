package com.lms.LearningManagementSystem.Model.User;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginRequest {
    private String email;
    private String password;
}
