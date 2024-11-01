package com.example.easyconnect.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @Email
    @NotBlank(message = "BLANK_EMAIL")
    String email;

    @NotBlank(message = "BLANK_PASSWORD")
    String password;
}
