package com.example.esayconnect.mapper;

import com.example.esayconnect.dto.reqResp.CompanyDTO;
import com.example.esayconnect.entity.Company;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

    Company toCompany(CompanyDTO companyDTO);

    CompanyDTO toCompanyDTO(Company company);
}
