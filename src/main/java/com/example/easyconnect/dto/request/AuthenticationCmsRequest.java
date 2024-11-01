package com.example.easyconnect.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationCmsRequest {
    @NotBlank(message = "BLANK_USERNAME")
    String username;

    @NotBlank(message = "BLANK_PASSWORD")
    String password;
}
