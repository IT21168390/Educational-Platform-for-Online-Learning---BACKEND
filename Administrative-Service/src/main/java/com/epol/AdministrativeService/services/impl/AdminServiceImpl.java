package com.epol.AdministrativeService.services.impl;

import com.epol.AdministrativeService.consts.EnrollmentStatus;
import com.epol.AdministrativeService.consts.Status;
import com.epol.AdministrativeService.dao.CourseDAO;
import com.epol.AdministrativeService.dao.EnrollmentDAO;
import com.epol.AdministrativeService.models.Course;
import com.epol.AdministrativeService.models.Enrollment;
import com.epol.AdministrativeService.repositories.CourseRepository;
//import com.epol.AdministrativeService.repositories.EnrollmentRepository;
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
        @Override
        public CourseDAO updateStatus(String courseId, String courseName, Status status) {
            Course course = courseRepository.findById(courseId).orElseThrow(NoSuchElementException::new);
            //course.setName(courseName);
            course.setStatus(status);

            Course updatedCourse = courseRepository.save(course);
            CourseDAO courseDAO = new CourseDAO(updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getCourse_content(), updatedCourse.getPrice(), updatedCourse.getStatus());
            return courseDAO;
        }
}
