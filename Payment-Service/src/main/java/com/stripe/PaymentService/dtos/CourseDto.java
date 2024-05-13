package com.stripe.PaymentService.dtos;

import com.stripe.PaymentService.consts.CommonStatus;
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
