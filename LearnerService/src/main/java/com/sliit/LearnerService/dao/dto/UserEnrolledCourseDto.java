package com.sliit.LearnerService.dao.dto;

import com.sliit.LearnerService.util.enums.CommonStatus;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link com.sliit.LearnerService.dao.domain.UserEnrolledCourse}
 */
@Data
public class UserEnrolledCourseDto implements Serializable {
    private Integer id;
    private String enrolledCourseId;
    private Date enrolledDate;
    private CommonStatus status;
    private String userId;
    private CourseDto courseDto;
    private Boolean quiz;
    private Boolean note;
    private Boolean video;
}