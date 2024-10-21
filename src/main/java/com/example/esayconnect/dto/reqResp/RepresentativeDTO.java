package com.example.esayconnect.dto.reqResp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
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
