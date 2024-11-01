package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.reqResp.PotentialPartnerConditionProvinceDTO;
import com.example.easyconnect.entity.PotentialPartnerConditionProvince;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PotentialPartnerConditionProvinceMapper {
    PotentialPartnerConditionProvince toPotentialPartnerConditionProvince(PotentialPartnerConditionProvinceDTO potentialPartnerConditionProvinceDTO);
}
