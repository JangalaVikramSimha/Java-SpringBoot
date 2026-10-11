package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    /** Overload: add a student without email. */
    public Student addStudent(String firstName, String lastName, String batch) throws InvalidInputException {
        return addStudent(firstName, lastName, null, batch);
    }

    /** Overload: add a student with email. */
    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        InputValidator.requireNonEmpty(firstName, "First name");
        InputValidator.requireNonEmpty(lastName, "Last name");
        InputValidator.requireNonEmpty(batch, "Batch");
        if (email != null && !email.trim().isEmpty() && !InputValidator.isValidEmail(email.trim())) {
            throw new InvalidInputException("Invalid email format.");
        }
        String cleanEmail = (email == null || email.trim().isEmpty()) ? null : email.trim();
        Student s = new Student( firstName.trim(), lastName.trim(), cleanEmail, batch.trim());
        students.add(s);
        return s;
    }

    public List<Student> listStudents() {
        return new ArrayList<>(students);
    }

    public Student findStudentById(int id) throws EntityNotFoundException {
        for (Student s : students) {
            if (s.getId() == id) {
                return s;
            }
        }
        throw new EntityNotFoundException("Student with ID " + id + " not found.");
    }

    public void updateStudent(int id, String firstName, String lastName, String batch)
            throws EntityNotFoundException {
        Student s = findStudentById(id);
        if (firstName != null && !firstName.trim().isEmpty()) s.setFirstName(firstName.trim());
        if (lastName != null && !lastName.trim().isEmpty()) s.setLastName(lastName.trim());
        if (batch != null && !batch.trim().isEmpty()) s.setBatch(batch.trim());
    }

    public void deactivateStudent(int id) throws EntityNotFoundException {
        findStudentById(id).setActive(false);
    }

    public void removeStudent(int id) throws EntityNotFoundException {
        students.remove(findStudentById(id));
    }
}
