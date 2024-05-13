package com.notificationService.notificationService.service;

import com.notificationService.notificationService.model.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmailToAdmin(Feedback feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(feedback.getTitle());
        message.setTo("vasjanani3@gmail.com");
        message.setFrom("vasjanani3@gmail.com");
        message.setText(feedback.getDetails());

        javaMailSender.send(message);
    }
    
}
