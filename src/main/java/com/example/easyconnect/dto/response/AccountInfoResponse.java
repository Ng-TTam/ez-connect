package com.example.easyconnect.dto.response;

import com.example.easyconnect.enums.LoginType;
import com.example.easyconnect.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;


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
