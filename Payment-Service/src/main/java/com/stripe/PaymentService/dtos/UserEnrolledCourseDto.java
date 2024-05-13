package com.stripe.PaymentService.dtos;

import com.stripe.PaymentService.consts.CommonStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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