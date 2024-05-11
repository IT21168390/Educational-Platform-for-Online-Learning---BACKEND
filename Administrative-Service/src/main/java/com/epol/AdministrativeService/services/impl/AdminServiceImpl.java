package com.epol.AdministrativeService.services.impl;

import com.epol.AdministrativeService.consts.EnrollmentStatus;
import com.epol.AdministrativeService.consts.Status;
import com.epol.AdministrativeService.dao.CourseDAO;
import com.epol.AdministrativeService.dao.EnrollmentDAO;
import com.epol.AdministrativeService.models.Course;
import com.epol.AdministrativeService.models.Enrollment;
import com.epol.AdministrativeService.repositories.CourseRepository;
import com.epol.AdministrativeService.repositories.EnrollmentRepository;
import com.epol.AdministrativeService.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

        @Override
        public CourseDAO updateStatus(String courseId, String courseName, Status status) {
            Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
            course.setName(courseName);
            course.setStatus(status);

            Course updatedCourse = courseRepository.save(course);
            CourseDAO courseDAO = new CourseDAO(updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getCourse_content(), updatedCourse.getPrice(), updatedCourse.getStatus());
            return courseDAO;
        }

    @Override
    public EnrollmentDAO approveEnrollment(String enrollmentId, EnrollmentStatus Status) {
        Optional<Enrollment> optionalEnrollment = enrollmentRepository.findById(enrollmentId);
        if (optionalEnrollment.isPresent()) {
            Enrollment enrollment = optionalEnrollment.get();
            // Update enrollment status to "APPROVED"
            enrollment.setEnrollmentStatus(Status);
            Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
            EnrollmentDAO enrollmentDAO = new EnrollmentDAO(updatedEnrollment.getId(), updatedEnrollment.getLearnerId(), updatedEnrollment.getEnrollmentStatus());
            System.out.println("Enrollment updated successfully.");
            return enrollmentDAO;

        } else {
            System.out.println("Enrollment not found.");
            return null;

        }
    }

    @Override
    public List<CourseDAO> findAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDAO> courseDAOList = new ArrayList<>();
        for (Course course:
                courses) {
            CourseDAO courseDAO = new CourseDAO();
            courseDAO.setId(course.getId());
            courseDAO.setName(course.getName());
            courseDAO.setCourse_content(course.getCourse_content());
            courseDAO.setPrice(course.getPrice());
            courseDAO.setStatus(course.getStatus());

            courseDAOList.add(courseDAO);
        }
        return courseDAOList;
    }
}
