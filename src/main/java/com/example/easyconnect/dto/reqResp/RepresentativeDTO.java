package com.example.easyconnect.dto.reqResp;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepresentativeDTO {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String name;
    String position;
    String email;
    String phone;
    String zaloInfo;
    String telegramInfo;
    String whatsappInfo;
}
