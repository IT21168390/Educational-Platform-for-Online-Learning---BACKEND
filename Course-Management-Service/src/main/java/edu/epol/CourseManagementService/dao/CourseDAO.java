package edu.epol.CourseManagementService.dao;

import edu.epol.CourseManagementService.consts.Status;
import edu.epol.CourseManagementService.models.CourseContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDAO {
    private String id;
    private String name;
    private CourseContent course_content;
    private double price;
    private Status status;
}
