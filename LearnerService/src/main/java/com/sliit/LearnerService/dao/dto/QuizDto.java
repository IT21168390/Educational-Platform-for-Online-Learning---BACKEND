package com.sliit.LearnerService.dao.dto;

import lombok.Data;

@Data
public class QuizDto {
	private String id;
    private String question;
    private String answer;
    private String[] options = new String[4];
    private float weight;
}
