package com.example.esayconnect.mapper;

import com.example.esayconnect.dto.reqResp.PotentialPartnerConditionDTO;
import com.example.esayconnect.entity.PotentialPartnerCondition;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PotentialPartnerConditionMapper {

    PotentialPartnerCondition toPotentialPartnerCondition(PotentialPartnerConditionDTO potentialPartnerConditionDTO);

    PotentialPartnerConditionDTO toPotentialPartnerConditionDTO(PotentialPartnerCondition potentialPartnerCondition);
}
