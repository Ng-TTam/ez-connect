package com.example.esayconnect.mapper;

import com.example.esayconnect.dto.reqResp.RepresentativeDTO;
import com.example.esayconnect.entity.Representative;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepresentativeMapper {
    Representative toRepresentative(RepresentativeDTO representativeDTO);

    RepresentativeDTO toRepresentativeDTO(Representative representative);
}
