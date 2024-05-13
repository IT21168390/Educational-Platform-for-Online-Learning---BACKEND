package com.stripe.PaymentService.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CourseContentDto {
	private LectureNoteDto lecture_note;
    private VideoDto video;
    private List<QuizDto> quizzes;

}
