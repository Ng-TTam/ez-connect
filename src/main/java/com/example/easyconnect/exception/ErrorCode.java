package com.example.easyconnect.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(3001, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN(3002,"Your token is expired", HttpStatus.UNAUTHORIZED),

    INVALID_KEY(3001, "Uncategorized error", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(3002, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    INVALID_NUMBER(3003, "Phone number is invalid", HttpStatus.BAD_REQUEST),
    INVALID_GENDER(3004, "Gender must be one of the following: male, female, other", HttpStatus.BAD_REQUEST),
    OTP_INVALID(3005, "OTP is valid", HttpStatus.BAD_REQUEST),

    BLANK_USER(4001, "Username is required", HttpStatus.BAD_REQUEST),
    BLANK_NUMBER(4002, "Number is required", HttpStatus.BAD_REQUEST),
    BLANK_EMAIL(4003, "Email is required", HttpStatus.BAD_REQUEST),
    BLANK_GENDER(4004, "Gender is required", HttpStatus.BAD_REQUEST),
    BLANK_PASSWORD(4005, "Password is required", HttpStatus.BAD_REQUEST),
    BLANK_USERNAME(4006, "User name is required", HttpStatus.BAD_REQUEST),

    DOB_FUTURE(5000, "Date of birth must be in the past", HttpStatus.BAD_REQUEST),

    EMAIL_NOT_EXISTED(6001, "Email is not existed", HttpStatus.NOT_FOUND),
    KEY_NOT_EXISTED(6002, "Email is not existed", HttpStatus.NOT_FOUND),
    ACCOUNT_NOT_EXISTED(6003, "Account is not existed", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(6004, "Email is existed", HttpStatus.BAD_REQUEST),

    WRONG_CURRENT_PASS(7001, "Current password is wrong", HttpStatus.BAD_REQUEST),
    ERROR_SEND_OTP(7002,"Some error occur when send otp", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_UPLOAD_IMAGE(7003,"Some error occur when upload image", HttpStatus.INTERNAL_SERVER_ERROR),
    ERROR_DOWNLOAD_IMAGE(7004,"Some error occur when download image", HttpStatus.INTERNAL_SERVER_ERROR),

    UPDATE_COMPANY_ERROR(8001, "Update company info fail", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_REPRESENTATIVE_ERROR(8001, "Update representative info fail", HttpStatus.INTERNAL_SERVER_ERROR),
    UPDATE_POTENTIAL_PARTNER_CONDITION_ERROR(8002, "Update potential partner condition info fail", HttpStatus.INTERNAL_SERVER_ERROR),

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
