package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.reqResp.RepresentativeDTO;
import com.example.easyconnect.dto.request.RepresentativeUpdateRequest;
import com.example.easyconnect.dto.response.RepresentativeUpdateResponse;
import com.example.easyconnect.entity.Representative;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RepresentativeMapper {
    Representative toRepresentative(RepresentativeDTO representativeDTO);

    RepresentativeDTO toRepresentativeDTO(Representative representative);

    void updateRepresentative(@MappingTarget Representative representative, RepresentativeUpdateRequest representativeUpdateRequest);

    RepresentativeUpdateResponse toRepresentativeUpdateResponse(Representative representative);
}
