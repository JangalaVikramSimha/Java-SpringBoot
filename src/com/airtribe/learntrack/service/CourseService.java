package com.airtribe.learntrack.service;

import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.util.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private final List<Course> courses = new ArrayList<>();

    public Course addCourse(String name, String description, int durationInWeeks) throws InvalidInputException {
        InputValidator.requireNonEmpty(name, "Course name");
        if (durationInWeeks <= 0) {
            throw new InvalidInputException("Duration must be greater than 0.");
        }
        Course c = new Course(name.trim(), description == null ? "" : description.trim(), durationInWeeks);
        courses.add(c);
        return c;
    }

    public List<Course> listCourses() {
        return new ArrayList<>(courses);
    }

    public Course findCourseById(int id) throws EntityNotFoundException {
        for (Course c : courses) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new EntityNotFoundException("Course with ID " + id + " not found.");
    }

    public void setCourseActive(int id, boolean active) throws EntityNotFoundException {
        findCourseById(id).setActive(active);
    }

    public void removeCourse(int id) throws EntityNotFoundException {
        courses.remove(findCourseById(id));
    }
}
