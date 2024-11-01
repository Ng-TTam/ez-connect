package com.example.easyconnect.dto.reqResp;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AnnualRevenueCategoryDTO {
    int id;
    String name;
    long maxRange;
    long minRange;
}
