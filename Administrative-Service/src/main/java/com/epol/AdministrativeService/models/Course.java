package com.epol.AdministrativeService.models;

import com.epol.AdministrativeService.consts.Status;
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
    private String thumbnail;
    private String description;
    private double price;
    private Status status;
}
