package com.example.easyconnect.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepresentativeUpdateRequest {
    @NotBlank
    String name;

    @NotBlank
    String position;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String phone;

    String zaloInfo;
    String telegramInfo;
    String whatsappInfo;
}
