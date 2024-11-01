package com.example.easyconnect.dto.request;

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

import java.util.List;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PotentialPartnerConditionRequest {
    List<ScaleCategory> scales;
    List<AnnualRevenueCategoryDTO> annualRevenues;
    List<PotentialPartnerConditionCountryDTO> potentialPartnerConditionCountries;
    List<PotentialPartnerConditionProvinceDTO> potentialPartnerConditionProvinces;
    List<PotentialPartnerActivityAreaDTO> potentialPartnerActivityAreas;
}
