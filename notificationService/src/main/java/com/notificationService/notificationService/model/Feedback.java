package com.notificationService.notificationService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
@Document(collection = "feedback")



public class Feedback {

	@Id
    private String id;
    private String title;
    private String details;
}
