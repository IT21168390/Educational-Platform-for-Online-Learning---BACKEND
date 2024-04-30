package edu.epol.CourseManagementService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseContent {
    private LectureNote lecture_note;
    private Video video;
    private List<Quiz> quizzes;
}
