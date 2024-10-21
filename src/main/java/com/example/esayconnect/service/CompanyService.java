package com.example.esayconnect.service;

import com.example.esayconnect.dto.reqResp.CompanyDTO;
import com.example.esayconnect.entity.AccountInfo;
import com.example.esayconnect.entity.Company;
import com.example.esayconnect.entity.PotentialPartnerCondition;
import com.example.esayconnect.entity.Representative;
import com.example.esayconnect.exception.AppException;
import com.example.esayconnect.exception.ErrorCode;
import com.example.esayconnect.mapper.CompanyMapper;
import com.example.esayconnect.mapper.PotentialPartnerConditionMapper;
import com.example.esayconnect.mapper.RepresentativeMapper;
import com.example.esayconnect.repository.AccountInfoRepository;
import com.example.esayconnect.repository.CompanyRepository;
import com.example.esayconnect.repository.PotentialPartnerConditionRepository;
import com.example.esayconnect.repository.RepresentativeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RepresentativeRepository representativeRepository;
    @Autowired
    PotentialPartnerConditionRepository potentialPartnerConditionRepository;

    @Autowired
    CompanyMapper companyMapper;
    @Autowired
    RepresentativeMapper representativeMapper;
    @Autowired
    PotentialPartnerConditionMapper potentialPartnerConditionMapper;

    public CompanyDTO getCompanyByAccount(){
        var context = SecurityContextHolder.getContext();
        String accountId = context.getAuthentication().getName();

        Company company = companyRepository.findByAccountId(accountId);

        CompanyDTO companyDTO = companyMapper.toCompanyDTO(company);
        companyDTO.setRepresentative(
                representativeMapper.toRepresentativeDTO(
                        representativeRepository.findByCompany(company)
                                .orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR)))
        );
        companyDTO.setPotentialPartnerConditions(
                potentialPartnerConditionRepository.findByCompany(company)
                        .stream()
                        .map(potentialPartnerConditionMapper::toPotentialPartnerConditionDTO)
                        .toList()
        );

        return companyDTO;
    }
}
