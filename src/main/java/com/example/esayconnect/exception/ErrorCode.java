package com.example.esayconnect.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(301, "Uncategorized error", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTED(302, "Email is existed", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(304, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER(305, "Phone number is invalid", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXIST(306, "Email is not existed", HttpStatus.BAD_REQUEST),
    INVALID_GENDER(307, "Gender must be one of the following: male, female, other", HttpStatus.BAD_REQUEST),
    BLANK_USER(308, "Username is required", HttpStatus.BAD_REQUEST),
    BLANK_NUMBER(309, "Number is required", HttpStatus.BAD_REQUEST),
    BLANK_EMAIL(310, "Email is required", HttpStatus.BAD_REQUEST),
    BLANK_GENDER(311, "Gender is required", HttpStatus.BAD_REQUEST),
    BLANK_PASSWORD(312, "Password is required", HttpStatus.BAD_REQUEST),
    BLANK_USERNAME(313, "Name login is required", HttpStatus.BAD_REQUEST),
    DOB_FUTURE(314, "Date of birth must be in the past", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_EXISTED(315, "Email is not existed", HttpStatus.NOT_FOUND),
    KEY_NOT_EXISTED(320, "Email is not existed", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_EXISTED(320, "Account is not existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(316, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    WRONG_CURRENT_PASS(317, "Current password is wrong", HttpStatus.BAD_REQUEST),
    OTP_INVALID(318, "OTP is valid", HttpStatus.BAD_REQUEST),
    ERROR_SEND_OTP(319,"Some error occur when send otp", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_ERROR(500,"System error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}