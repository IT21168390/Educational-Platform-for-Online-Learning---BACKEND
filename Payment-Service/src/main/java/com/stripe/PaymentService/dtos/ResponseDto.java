package com.stripe.PaymentService.dtos;

import com.stripe.PaymentService.consts.ApiStatus;
import lombok.Data;

import java.util.List;

@Data
public class ResponseDto {
    private boolean isSuccess;
    private ApiStatus apiStatus;
    private String error;
    private List<CourseDto> courseDtoList;
}
