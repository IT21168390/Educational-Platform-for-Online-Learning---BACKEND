package com.epol.AdministrativeService.controllers;

import com.epol.AdministrativeService.consts.EnrollmentStatus;
import com.epol.AdministrativeService.consts.Status;
import com.epol.AdministrativeService.dao.CourseDAO;
import com.epol.AdministrativeService.dao.EnrollmentDAO;
import com.epol.AdministrativeService.dao.UpdateCourseRequestDAO;
import com.epol.AdministrativeService.dao.UpdateEnrollmentRequestDAO;
import com.epol.AdministrativeService.services.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;

    @PutMapping("/courses/{courseId}/status")
    public ResponseEntity<CourseDAO> updateCourseStatus(
            @PathVariable String courseId,
            @RequestBody UpdateCourseRequestDAO request) {

        // Extract courseName and status from the request body
        String courseName = request.getCourseName();
        Status status = request.getStatus();

        // Call the service to update the status
        CourseDAO updatedCourse = adminService.updateStatus(courseId, courseName, status);
        LOGGER.info("Course Updated! : {}", updatedCourse);
        // Return the updated course
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }


    @PutMapping("/enrollments/{enrollmentId}/status")
    public ResponseEntity<EnrollmentDAO> approveEnrollment(
            @PathVariable String enrollmentId,
            @RequestBody UpdateEnrollmentRequestDAO request) {

        EnrollmentStatus status = request.getStatus();

        // Call the service method to approve enrollment
        EnrollmentDAO approvedEnrollment = adminService.approveEnrollment(enrollmentId, status);
//        return new ResponseEntity<>(approvedEnrollment, HttpStatus.OK);

        if (approvedEnrollment != null) {
            return ResponseEntity.ok(approvedEnrollment);
        } else {
            // Return 404 if enrollment is not found
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/")
    public List<CourseDAO> getAllCourses() {
        return adminService.findAll();
    }
}
