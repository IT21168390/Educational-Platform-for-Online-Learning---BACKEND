package com.sliit.LearnerService.dao.dto;

import java.util.List;

import lombok.Data;

@Data
public class CourseContentDto {
	private LectureNoteDto lecture_note;
    private VideoDto video;
    private List<QuizDto> quizzes;

}
