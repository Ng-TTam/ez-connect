package com.example.esayconnect.dto.response;

import com.example.esayconnect.enums.LoginType;
import com.example.esayconnect.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfoResponse {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    String email;
    LoginType loginType;
    String language;
    Status status;
}
