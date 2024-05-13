package com.epol.AdministrativeService.controllers;

import com.epol.AdministrativeService.clients.CourseClient;
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
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private static final Logger LOGGER
            = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;
    @Autowired
    private CourseClient courseClient;

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

    @GetMapping("/api/v1/courses/public/all")
    public ResponseEntity<List<CourseDAO>> getAllCourses(){
        try {
            List<CourseDAO> courseList = courseClient.getAllCourses().getBody();
            return new ResponseEntity<>(courseList, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
