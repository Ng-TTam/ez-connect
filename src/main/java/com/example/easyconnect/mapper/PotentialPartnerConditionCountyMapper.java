package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.reqResp.PotentialPartnerConditionCountryDTO;
import com.example.easyconnect.entity.PotentialPartnerConditionCountry;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PotentialPartnerConditionCountyMapper {
    PotentialPartnerConditionCountry toPotentialPartnerConditionCountry(PotentialPartnerConditionCountryDTO potentialPartnerConditionCountryDTO);
}
