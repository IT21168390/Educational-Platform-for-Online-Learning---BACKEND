package edu.epol.CourseManagementService.models;

import edu.epol.CourseManagementService.consts.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Document("courses")
@Data
public class Course {
    @Id
    private String id;
    private String name;
    private CourseContent course_content;
    private double price;
    private Status status = Status.PENDING;
}
