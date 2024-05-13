package com.stripe.PaymentService.consts;

import lombok.Getter;

@Getter
public enum ApiStatus {
    SUCCESS("Success"),
    BAD_REQUEST("Bad Request"),
    SOMETHING_WENT_WRONG("Something went wrong"),
    UPDATE_SUCCESS("Successfully updated"),
    INPUT_NULL("Input null"),
    DELETE_SUCCESS("Successfully Deleted"),
    NOT_FOUND("Not found");
    
    private final String message;
    ApiStatus(String message) {
        this.message = message;
    }
}
