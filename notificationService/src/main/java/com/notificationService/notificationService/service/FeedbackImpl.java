package com.notificationService.notificationService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.notificationService.notificationService.model.Feedback;
import com.notificationService.notificationService.repo.FeedbackRepo;


@Service
public class FeedbackImpl implements FeedbackService{

	@Autowired
	private FeedbackRepo feedbackRepo;

	@Override
	public String save(Feedback feedback) {

		return feedbackRepo.save(feedback).getId();
	}
}
