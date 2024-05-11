package com.sliit.LearnerService.dao.dto;

import lombok.Data;

@Data
public class VideoDto {
	private String video_Url;
    private float weight;
    private String description;
}
