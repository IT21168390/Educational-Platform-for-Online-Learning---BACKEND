package edu.epol.CourseManagementService.clients;

import edu.epol.CourseManagementService.dao.LearnerProgressDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;

@HttpExchange
public interface LearnerClient {
    // Access LearnerService to fetch the Learner Progress data for a specific course.
    @GetExchange(value = "/api/LearnerService/CourseLearnerProgresses/{courseId}")
    public @ResponseBody List<LearnerProgressDto> getLearnerProgresses(@PathVariable String courseId);
}
