package com.example.esayconnect.dto.reqResp;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProvinceDTO {
    int id;
    String name;
    CompanyDTO company;
}