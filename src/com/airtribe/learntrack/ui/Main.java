package com.airtribe.learntrack.ui;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.EnrollmentStatus;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;
import com.airtribe.learntrack.util.InputValidator;
import java.util.List;
import java.util.Scanner;

/** Console entry point: displays menus, reads input, delegates to services. */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final EnrollmentService enrollmentService =
            new EnrollmentService(studentService, courseService);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMainMenu();
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 1: studentMenu(); break;
                    case 2: courseMenu(); break;
                    case 3: enrollmentMenu(); break;
                    case 0:
                        running = false;
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose from the menu.");
                }
            } catch (InvalidInputException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n===== LearnTrack =====");
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("0. Exit");
    }

    // ---------- Student menu ----------
    private static void studentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add new student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by ID");
            System.out.println("4. Deactivate a student");
            System.out.println("0. Back");
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 1: addStudent(); break;
                    case 2: viewStudents(); break;
                    case 3: searchStudent(); break;
                    case 4: deactivateStudent(); break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addStudent() throws InvalidInputException {
        String first = readLine("First name: ");
        String last = readLine("Last name: ");
        String email = readLine("Email (optional, press Enter to skip): ");
        String batch = readLine("Batch: ");
        Student s = studentService.addStudent(first, last, email, batch);
        System.out.println("Student added: " + s);
    }

    private static void viewStudents() {
        List<Student> list = studentService.listStudents();
        if (list.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private static void searchStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Student ID: ");
        System.out.println(studentService.findStudentById(id));
    }

    private static void deactivateStudent() throws InvalidInputException, EntityNotFoundException {
        int id = readInt("Student ID to deactivate: ");
        studentService.deactivateStudent(id);
        System.out.println("Student deactivated.");
    }

    // ---------- Course menu ----------
    private static void courseMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add new course");
            System.out.println("2. View all courses");
            System.out.println("3. Activate a course");
            System.out.println("4. Deactivate a course");
            System.out.println("0. Back");
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 1: addCourse(); break;
                    case 2: viewCourses(); break;
                    case 3:
                        courseService.setCourseActive(readInt("Course ID: "), true);
                        System.out.println("Course activated.");
                        break;
                    case 4:
                        courseService.setCourseActive(readInt("Course ID: "), false);
                        System.out.println("Course deactivated.");
                        break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InvalidInputException | EntityNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void addCourse() throws InvalidInputException {
        String name = readLine("Course name: ");
        String desc = readLine("Description: ");
        int weeks = readInt("Duration in weeks: ");
        Course c = courseService.addCourse(name, desc, weeks);
        System.out.println("Course added: " + c);
    }

    private static void viewCourses() {
        List<Course> list = courseService.listCourses();
        if (list.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        for (Course c : list) {
            System.out.println(c);
        }
    }

    // ---------- Enrollment menu ----------
    private static void enrollmentMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Enrollment Management ---");
            System.out.println("1. Enroll a student in a course");
            System.out.println("2. View enrollments for a student");
            System.out.println("3. Mark enrollment as completed");
            System.out.println("4. Mark enrollment as cancelled");
            System.out.println("0. Back");
            try {
                int choice = readInt("Choose an option: ");
                switch (choice) {
                    case 1:
                        Enrollment e = enrollmentService.enroll(
                                readInt("Student ID: "), readInt("Course ID: "));
                        System.out.println("Enrolled: " + e);
                        break;
                    case 2: viewEnrollments(); break;
                    case 3:
                        enrollmentService.updateStatus(readInt("Enrollment ID: "), EnrollmentStatus.COMPLETED);
                        System.out.println("Enrollment marked as COMPLETED.");
                        break;
                    case 4:
                        enrollmentService.updateStatus(readInt("Enrollment ID: "), EnrollmentStatus.CANCELLED);
                        System.out.println("Enrollment marked as CANCELLED.");
                        break;
                    case 0: back = true; break;
                    default: System.out.println("Invalid option.");
                }
            } catch (InvalidInputException | EntityNotFoundException ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    private static void viewEnrollments() throws InvalidInputException, EntityNotFoundException {
        int studentId = readInt("Student ID: ");
        List<Enrollment> list = enrollmentService.listEnrollmentsForStudent(studentId);
        if (list.isEmpty()) {
            System.out.println("This student has no enrollments.");
            return;
        }
        for (Enrollment e : list) {
            System.out.println(e);
        }
    }

    // ---------- Input helpers ----------
    private static String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            System.out.println("\nInput closed. Exiting.");
            System.exit(0);
        }
        return scanner.nextLine();
    }

    private static int readInt(String prompt) throws InvalidInputException {
        return InputValidator.parseInt(readLine(prompt));
    }
}
