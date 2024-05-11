package com.epol.AdministrativeService.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseContent {
    private com.epol.AdministrativeService.models.LectureNote lecture_note;
    private Video video;
    private List<com.epol.AdministrativeService.models.Quiz> quizzes;
}
