package com.sliit.LearnerService.service.i;

import com.sliit.LearnerService.dao.dto.LearnerProgressDto;
import com.sliit.LearnerService.dao.dto.RequestDto;
import com.sliit.LearnerService.dao.dto.ResponseDto;

import java.util.List;

public interface LearnerService {
    public ResponseDto enrollCourse(RequestDto requestDto);
    public ResponseDto cancelCourseEnroll(RequestDto requestDto);
    public ResponseDto findUserEnrolledCoursesByUserId(RequestDto requestDto);
    public ResponseDto updateEnrolledCourseContent(RequestDto requestDto);

    public List<LearnerProgressDto> retrieveLearnerProgresses(String courseId);
    public LearnerProgressDto retrieveLearnerProgressOfUser(String courseId, String userId);

}
