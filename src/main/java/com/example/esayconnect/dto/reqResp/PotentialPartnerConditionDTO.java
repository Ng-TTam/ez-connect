package com.example.esayconnect.dto.reqResp;

import com.example.esayconnect.entity.AnnualRevenueCategory;
import com.example.esayconnect.entity.ScaleCategory;
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
public class PotentialPartnerConditionDTO {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    ScaleCategory scale;
    AnnualRevenueCategory annualRevenue;
}
