package com.notificationService.notificationService.controller;

import com.notificationService.notificationService.model.Feedback;
import com.notificationService.notificationService.service.EmailService;
import com.notificationService.notificationService.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("api/v1/users")
public class FeedbackController {

	private final FeedbackService feedbackService;
	private final EmailService emailService;

	@Autowired
	public FeedbackController(FeedbackService feedbackService, EmailService emailService) {
		this.feedbackService = feedbackService;
		this.emailService = emailService;
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveFeedback(@RequestBody Feedback feedback) {
		feedbackService.save(feedback);
		emailService.sendEmailToAdmin(feedback);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/notification")
	public ResponseEntity<Object> enrollment(@RequestBody String test) {
		//emailService.sendEmailToAdmin(feedback);

		return ResponseEntity.ok().build();
	}
}
