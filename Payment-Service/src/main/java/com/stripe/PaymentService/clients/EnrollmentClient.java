package com.stripe.PaymentService.clients;

import com.stripe.PaymentService.dtos.EnrollmentRequestDto;
import com.stripe.PaymentService.dtos.ResponseDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface EnrollmentClient {
    @PostExchange("/enrollCourse")
    public @ResponseBody ResponseDto enrollCourse(@RequestBody EnrollmentRequestDto requestDto);

}
