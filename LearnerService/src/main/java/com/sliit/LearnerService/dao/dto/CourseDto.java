package com.sliit.LearnerService.dao.dto;

import com.sliit.LearnerService.util.enums.CommonStatus;

import lombok.Data;

@Data
public class
CourseDto {
    private String id;
    private String name;
    private CourseContentDto course_content;
    private String thumbnail;
    private String description;
    private double price;
    private CommonStatus status;
}
