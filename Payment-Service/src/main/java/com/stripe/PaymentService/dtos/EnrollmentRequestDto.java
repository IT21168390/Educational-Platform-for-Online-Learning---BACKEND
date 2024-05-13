package com.stripe.PaymentService.dtos;

import lombok.Data;

@Data
public class EnrollmentRequestDto {
    private UserEnrolledCourseDto userEnrolledCourseDto;
    private String userId;
    
    private String contentId;
    private Long quizMarks;

}
