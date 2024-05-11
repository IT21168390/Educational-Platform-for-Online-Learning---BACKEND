package com.notificationService.notificationService.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.notificationService.notificationService.model.Feedback;

@Repository
public interface FeedbackRepo extends MongoRepository<Feedback, String> {

}
