package com.lms.LearningManagementSystem;

import com.lms.LearningManagementSystem.Model.User.*;
import com.lms.LearningManagementSystem.Service.CourseService;
import com.lms.LearningManagementSystem.Service.NotificationService;
import com.lms.LearningManagementSystem.Service.AssessmentService;
import com.lms.LearningManagementSystem.Service.UserService.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class UserServiceTest {

	@Mock
	private CourseService courseService;

	@Mock
	private NotificationService notificationService;

	@Mock
	private AssessmentService assessmentService;

	private UserService userService;

	private User adminUser;
	private User studentUser;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		userService = new UserService(courseService, notificationService, assessmentService);

		// Initialize test users
		adminUser = new Admin();
		adminUser.setId(1L);
		adminUser.setName("Admin User");
		adminUser.setEmail("admin@example.com");
		adminUser.setPassword("password");
		adminUser.setRole("admin");

		studentUser = new Student();
		studentUser.setId(2L);
		studentUser.setName("Student User");
		studentUser.setEmail("student@example.com");
		studentUser.setPassword("password");
		studentUser.setRole("student");

		// Add users to userStore for testing
		UserService.userStore.put(adminUser.getId(), adminUser);
		UserService.userStore.put(studentUser.getId(), studentUser);
	}

	@Test
	void testAddUser() {
		// Given
		User newUser = new User();
		newUser.setName("Instructor User");
		newUser.setEmail("instructor@example.com");
		newUser.setPassword("password");
		newUser.setRole("instructor");

		// When
		User addedUser = userService.addUser(newUser);

		// Then
		assertNotNull(addedUser, "User should be added successfully");
		assertEquals("Instructor User", addedUser.getName(), "User name should match");
		assertEquals("instructor@example.com", addedUser.getEmail(), "User email should match");
		assertEquals("Instructor", addedUser.getRole(), "User role should match");
	}

	@Test
	void testUpdateUser() {
		// Given
		adminUser.setName("Updated Admin User");

		// When
		User updatedUser = userService.updateUser(adminUser, adminUser.getId());

		// Then
		assertNotNull(updatedUser, "Updated user should not be null");
		assertEquals("Updated Admin User", updatedUser.getName(), "User name should be updated");
	}

	@Test
	void testDeleteUser() {
		// Given
		Long userId = studentUser.getId();

		// When
		userService.deleteUser(userId);

		// Then
		assertNull(UserService.userStore.get(userId), "User should be deleted from userStore");
	}

	@Test
	void testGetUserById() {
		// Given
		Long userId = adminUser.getId();

		// When
		User foundUser = userService.getUserById(userId);

		// Then
		assertNotNull(foundUser, "User should be found by ID");
		assertEquals(adminUser.getId(), foundUser.getId(), "User ID should match");
	}

	@Test
	void testListUsersByRole() {
		// Given
		String role = "student";

		// When
		List<User> users = userService.getUsersByRole(role);

		// Then
		assertNotNull(users, "List of users should not be null");
		assertEquals(1, users.size(), "There should be one student user");
		assertTrue(users.get(0) instanceof Student, "The user should be a student");
	}


}
