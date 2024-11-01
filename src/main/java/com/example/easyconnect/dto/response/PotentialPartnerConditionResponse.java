package com.example.easyconnect.dto.response;

import com.example.easyconnect.dto.reqResp.AnnualRevenueCategoryDTO;
import com.example.easyconnect.dto.reqResp.PotentialPartnerActivityAreaDTO;
import com.example.easyconnect.dto.reqResp.PotentialPartnerConditionCountryDTO;
import com.example.easyconnect.dto.reqResp.PotentialPartnerConditionProvinceDTO;
import com.example.easyconnect.entity.ScaleCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PotentialPartnerConditionResponse {
    String id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<ScaleCategory> scales;
    List<AnnualRevenueCategoryDTO> annualRevenues;
    List<PotentialPartnerConditionCountryDTO> potentialPartnerConditionCountries;
    List<PotentialPartnerConditionProvinceDTO> potentialPartnerConditionProvinces;
    List<PotentialPartnerActivityAreaDTO> potentialPartnerActivityAreas;
}
