# 📘 Learning Management System (LMS) - Requirements

## ✅ Functional Requirements (FRs)

### FR1: Quiz Management by Instructors
- Instructors can create, edit, and delete quizzes.
- Each quiz includes: title, description, time limit, and multiple question types (MCQs, short answers).
- Quizzes can have due dates and be published/unpublished.
- Example: An instructor creates a "Java Basics" quiz with 10 MCQs.

### FR2: Assignment Management by Instructors
- Instructors can create, update, and delete assignments.
- Assignments include title, description, due date, and upload instructions.
- Example: Instructor uploads a template with a 3-day deadline.

### FR3: Student Quiz Participation
- Students can view available quizzes, take them before the deadline, and receive auto-grades (for MCQs).
- Example: A student takes a "Java OOP" quiz and gets instant feedback.

### FR4: Assignment Submission by Students
- Students can upload assignment files before due dates.
- System checks file type and size.
- Example: A student uploads a PDF for a lab report.

### FR5: Instructor Grading
- Instructors view submissions, assign grades, and provide feedback.
- Example: An instructor gives 8/10 for a short-answer question.

### FR6: Export Quiz Results to PDF (NEW FEATURE)
- Instructors can export all quiz results to PDF (includes name, score, quiz title, date).
- Example: PDF download of “Final Exam Quiz” scores.

### FR7: Course and Lesson Management
- Instructors manage course content (texts, videos, files).
- Students access lessons by course.
- Example: A course includes PDFs and videos per week.

### FR8: Notifications System
- Notifications for new assignments, deadlines, grades.
- Email or in-app delivery.
- Example: Students get notified 24h before quiz due.

### FR9: User Role Management
- Roles: Admin, Instructor, Student.
- Access and visibility depend on role.
- Example: Admin manages users, Instructors manage content.

---

## 📐 Non-Functional Requirements (NFRs)

### NFR1: Maintainability
- Follow Java standards and SonarQube recommendations.
- Clean, well-structured, and commented code.

### NFR2: Performance
- Pages load within 2 seconds.
- Pagination for lists of quizzes/assignments.
- Supports 100+ concurrent users.

### NFR3: Reliability
- Custom error handling (e.g., "Quiz not found").
- Log errors with timestamp and context.

### NFR4: Security
- Role-based access control.
- Input validation to prevent attacks.

### NFR5: Usability
- Simple, clean, consistent UI.
- Tooltips and clear messages for users.

### NFR6: Compatibility
- Java 11+, Spring Boot.
- REST API supports frontend and external clients.

### NFR7: Documentation
- Javadoc and markdown docs for each module.
- Developer setup instructions in README.

---

🛠️ **Last updated:** May 2025
"""
