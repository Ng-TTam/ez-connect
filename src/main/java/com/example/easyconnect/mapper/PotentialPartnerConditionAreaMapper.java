package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.reqResp.PotentialPartnerActivityAreaDTO;
import com.example.easyconnect.entity.PotentialPartnerActivityArea;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PotentialPartnerConditionAreaMapper {
    PotentialPartnerActivityArea toPotentialPartnerActivityArea(PotentialPartnerActivityAreaDTO potentialPartnerActivityAreaDTO);
}
