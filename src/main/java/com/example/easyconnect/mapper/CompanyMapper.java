package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.reqResp.CompanyDTO;
import com.example.easyconnect.dto.request.CompanyUpdateRequest;
import com.example.easyconnect.dto.response.CompanyUpdateResponse;
import com.example.easyconnect.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toCompany(CompanyDTO companyDTO);

    CompanyDTO toCompanyDTO(Company company);

    CompanyUpdateResponse toCompanyUpdateResponse(Company company);

    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "coverImage", ignore = true)
//    @Mapping(target = "businessLicense", ignore = true)
//    @Mapping(target = "achievementAttached", ignore = true)
    void updateCompany(@MappingTarget Company company, CompanyUpdateRequest companyUpdateRequest);
}
