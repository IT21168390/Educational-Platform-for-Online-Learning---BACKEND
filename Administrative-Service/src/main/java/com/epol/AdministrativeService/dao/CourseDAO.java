package com.epol.AdministrativeService.dao;

import com.epol.AdministrativeService.consts.Status;
import com.epol.AdministrativeService.models.CourseContent;
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
