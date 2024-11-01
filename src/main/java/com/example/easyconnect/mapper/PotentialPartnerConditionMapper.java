package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.request.PotentialPartnerConditionRequest;
import com.example.easyconnect.dto.response.PotentialPartnerConditionResponse;
import com.example.easyconnect.entity.PotentialPartnerCondition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PotentialPartnerConditionMapper {

    PotentialPartnerCondition toPotentialPartnerCondition(PotentialPartnerConditionRequest potentialPartnerConditionDTO);

    PotentialPartnerConditionResponse toPotentialPartnerConditionResponse(PotentialPartnerCondition potentialPartnerCondition);

    void updatePotentialPartnerCondition(@MappingTarget PotentialPartnerCondition potentialPartnerCondition, PotentialPartnerConditionRequest potentialPartnerConditionRequest);
}
