package com.example.easyconnect.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AuthResetPassRequest {
    private String email;
    private String newPassword;
}
