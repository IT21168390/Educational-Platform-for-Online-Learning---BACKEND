package com.sliit.LearnerService.dao.dto;

import java.util.List;

import com.sliit.LearnerService.util.enums.ApiStatus;
import lombok.Data;

@Data
public class ResponseDto {
    private boolean isSuccess;
    private ApiStatus apiStatus;
    private String error;
    private List<CourseDto> courseDtoList;
}
