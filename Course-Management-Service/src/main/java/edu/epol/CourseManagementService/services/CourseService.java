package edu.epol.CourseManagementService.services;

import edu.epol.CourseManagementService.models.Course;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourseService {
    String uploadFile(MultipartFile file, String details) throws IOException;
    List<Course> findAll();
}
