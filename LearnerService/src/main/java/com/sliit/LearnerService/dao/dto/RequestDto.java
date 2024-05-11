package com.sliit.LearnerService.dao.dto;

import lombok.Data;

@Data
public class RequestDto {
    private UserEnrolledCourseDto userEnrolledCourseDto;
    private String userId;
    
    private String contentId;
    private Long quizMarks;

}
