package com.example.esayconnect.dto.reqResp;

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
public class CompanyBusinessLicenseDTO {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updateAt;
    String directory;
}
