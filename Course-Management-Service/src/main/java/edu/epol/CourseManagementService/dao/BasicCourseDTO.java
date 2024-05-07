package edu.epol.CourseManagementService.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicCourseDTO {
    private String name;
    private String description;
    private MultipartFile thumbnail;
    private double price;
}
