package com.example.easyconnect.dto.reqResp;

import com.example.easyconnect.enums.LoginType;
import com.example.easyconnect.enums.Status;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountInfoDTO {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    String email;
    LoginType loginType;
    String language;
    Status status;
}
