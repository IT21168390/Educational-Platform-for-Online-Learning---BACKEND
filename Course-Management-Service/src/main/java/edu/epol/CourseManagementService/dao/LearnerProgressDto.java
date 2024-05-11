package edu.epol.CourseManagementService.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LearnerProgressDto {
    private String courseId;
    private String userId;
    private Boolean quiz;
    private Boolean note;
    private Boolean video;
}
