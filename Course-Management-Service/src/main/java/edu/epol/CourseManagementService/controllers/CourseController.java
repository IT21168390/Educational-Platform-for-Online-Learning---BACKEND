package edu.epol.CourseManagementService.controllers;

import edu.epol.CourseManagementService.models.Course;
import edu.epol.CourseManagementService.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    /*@PostMapping("/")
    public String addCourse(@RequestParam("file") MultipartFile file, @RequestParam("details") String details){
        try {
            return courseService.uploadFile(file, details);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/")
    public List<Course> getAllCourses(){
        return courseService.findAll();
    }*/
}
