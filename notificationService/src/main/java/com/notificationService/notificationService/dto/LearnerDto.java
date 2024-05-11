package com.notificationService.notificationService.dto;

import lombok.Data;

@Data

public class LearnerDto {

	private Integer id;
	private String enrolledCourseId;
	//private CommonStatus status;
	private String userId;
	private Boolean quiz;
	private Boolean note;
	private Boolean video;
	

}
