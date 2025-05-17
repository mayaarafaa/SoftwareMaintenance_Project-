package com.lms.LearningManagementSystem.Service.UserService;

import com.lms.LearningManagementSystem.Model.User.*;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    public static final Map<Long, User> userStore = new ConcurrentHashMap<>();
    protected final AtomicLong idGenerator = new AtomicLong(1); // Atomic for synchronization
    @Autowired
    @Qualifier("courseService")
    protected static CourseService courseService;

    @Autowired
    @Qualifier("notificationService")
    protected static NotificationService notificationService;

    @Autowired
    protected static AssessmentService assessmentService;

    public UserService(CourseService courseService, NotificationService notificationService, AssessmentService assessmentService) {
        this.courseService = courseService;
        this.notificationService = notificationService;
        this.assessmentService = assessmentService;
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return new ArrayList<>(userStore.values());
    }

    // Retrieve a user by ID
    public User getUserById(Long id) {
        return userStore.get(id);
    }

    public User authenticate(String email, String password) {
        for (User user : userStore.values()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user; // User authenticated
            }
        }
        return null; // Invalid credentials
    }

    // Add a new user
    public User addUser(User user) {
        // Validate and set role
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("User role is required");
        }

        User newUser;
        switch (user.getRole().toLowerCase()) {
            case "admin":
                newUser = new Admin();
                break;
            case "instructor":
                newUser = new Instructor();
                break;
            case "student":
                newUser = new Student();
                break;
            default:
                throw new IllegalArgumentException("Invalid role. Valid roles are Admin, Instructor, and Student.");
        }

        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());

        long id = idGenerator.getAndIncrement();
        newUser.setId(id);
        userStore.put(id, newUser);
        return newUser;
    }

    // Update an existing user
    public User updateUser(User updateUser, Long id) {
        if (userStore.containsKey(id)) {
            updateUser.setId(id);
            userStore.put(id, updateUser);
            return updateUser;
        }
        return null;
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userStore.remove(id);
    }

    public ArrayList<User> listUsers(String role) {
        // Start with all users
        ArrayList<User> users = new ArrayList<>();

        // Filter by role if provided
        if (role != null && !role.isEmpty()) {
            for (User user : userStore.values()) {
                if (user.equals(user.getRole(), role)) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    public List<User> getUsersByRole(String role) {
        List<User> filteredUsers = new ArrayList<>();

        for (User user : userStore.values()) {
            if ("Student".equalsIgnoreCase(role) && user instanceof Student) {
                filteredUsers.add(user);
            } else if ("Instructor".equalsIgnoreCase(role) && user instanceof Instructor) {
                filteredUsers.add(user);
            } else if ("Admin".equalsIgnoreCase(role) && user instanceof Admin) {
                filteredUsers.add(user);
            }
        }

        return filteredUsers;
    }


}
