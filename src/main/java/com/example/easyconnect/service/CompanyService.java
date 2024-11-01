package com.example.easyconnect.service;

import com.example.easyconnect.dto.reqResp.CompanyDTO;
import com.example.easyconnect.dto.request.CompanyUpdateRequest;
import com.example.easyconnect.dto.request.PotentialPartnerConditionRequest;
import com.example.easyconnect.dto.request.RepresentativeUpdateRequest;
import com.example.easyconnect.dto.response.CompanyUpdateResponse;
import com.example.easyconnect.dto.response.PotentialPartnerConditionResponse;
import com.example.easyconnect.dto.response.RepresentativeUpdateResponse;
import com.example.easyconnect.entity.*;
import com.example.easyconnect.exception.AppException;
import com.example.easyconnect.exception.ErrorCode;
import com.example.easyconnect.mapper.*;
import com.example.easyconnect.repository.*;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyService {
    CompanyRepository companyRepository;
    RepresentativeRepository representativeRepository;
    PotentialPartnerConditionRepository potentialPartnerConditionRepository;
    CompanyBusinessLicenseRepository companyBusinessLicenseRepository;
    CompanyAchievementAttachedRepository companyAchievementAttachedRepository;
    PotentialPartnerConditionCountryRepository potentialPartnerConditionCountryRepository;
    PotentialPartnerConditionProvinceRepository potentialPartnerConditionProvinceRepository;
    PotentialPartnerActivityAreaRepository potentialPartnerActivityAreaRepository;

    CompanyMapper companyMapper;
    RepresentativeMapper representativeMapper;
    PotentialPartnerConditionMapper potentialPartnerConditionMapper;
    PotentialPartnerConditionAreaMapper potentialPartnerActivityAreaMapper;
    PotentialPartnerConditionCountyMapper potentialPartnerConditionCountyMapper;
    PotentialPartnerConditionProvinceMapper potentialPartnerConditionProvinceMapper;

    MinioService minioService;

    static final String AVATAR_FOLDER = "avatars";
    static final String COVER_IMAGE_FOLDER = "cover_images";
    static final String BUSINESS_LICENSE_FOLDER = "business_licenses";
    static final String ACHIEVEMENTS_ATTACHED_FOLDER = "achievements_attached";

    public CompanyDTO getCompanyByAccount(){
        var context = SecurityContextHolder.getContext();
        String accountId = context.getAuthentication().getName();

        Company company = companyRepository.findByAccountId(accountId).orElseThrow();

        CompanyDTO companyDTO = companyMapper.toCompanyDTO(company);
        companyDTO.setRepresentative(
                representativeMapper.toRepresentativeDTO(
                        representativeRepository.findByCompany(company)
                                .orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR)))
        );
        companyDTO.setPotentialPartnerConditions(
                potentialPartnerConditionMapper.toPotentialPartnerConditionResponse(
                        potentialPartnerConditionRepository.findByCompany(company)
                                .orElseThrow(() -> new AppException(ErrorCode.INTERNAL_ERROR)))
        );
        return companyDTO;
    }

    @Transactional
    public CompanyUpdateResponse updateCompanyInfo(CompanyUpdateRequest companyUpdateRequest){
        Company company = getCompany();
        companyMapper.updateCompany(company, companyUpdateRequest);

        if(companyUpdateRequest.getAvatar()!= null)
            company.setAvatar(minioService.uploadFile(AVATAR_FOLDER, companyUpdateRequest.getAvatar()));

        if(companyUpdateRequest.getCoverImage()!= null)
            company.setCoverImage(minioService.uploadFile(COVER_IMAGE_FOLDER,
                    companyUpdateRequest.getCoverImage()));
        try {
            companyRepository.save(company);

            CompanyBusinessLicense companyBusinessLicense = CompanyBusinessLicense.builder()
                    .company(company)
                    .directory(minioService.uploadFile(BUSINESS_LICENSE_FOLDER,
                            companyUpdateRequest.getBusinessLicense()))
                    .build();
            companyBusinessLicenseRepository.save(companyBusinessLicense);

            CompanyAchievementAttached companyAchievementAttached = CompanyAchievementAttached.builder()
                    .company(company)
                    .directory(minioService.uploadFile(ACHIEVEMENTS_ATTACHED_FOLDER,
                            companyUpdateRequest.getAchievementAttached()))
                    .build();
            companyAchievementAttachedRepository.save(companyAchievementAttached);

        }catch (Exception e){
            throw  new AppException(ErrorCode.UPDATE_COMPANY_ERROR);
        }
        return companyMapper.toCompanyUpdateResponse(company);
    }

    @Transactional
    public RepresentativeUpdateResponse updateRepresentative(RepresentativeUpdateRequest representativeUpdateRequest){
        Representative representative = representativeRepository.findByCompany(getCompany()).orElseThrow();

        representativeMapper.updateRepresentative(representative, representativeUpdateRequest);
        try {
            representativeRepository.save(representative);
        }catch (Exception e){
            throw  new AppException(ErrorCode.UPDATE_REPRESENTATIVE_ERROR);
        }
        return representativeMapper.toRepresentativeUpdateResponse(representative);
    }

    @Transactional
    public PotentialPartnerConditionResponse updatePotentialPartnerCondition(PotentialPartnerConditionRequest potentialPartnerConditionRequest){
        PotentialPartnerCondition potentialPartnerCondition = potentialPartnerConditionRepository.findByCompany(getCompany())
                .orElseThrow();

        potentialPartnerConditionMapper.updatePotentialPartnerCondition(potentialPartnerCondition,
                potentialPartnerConditionRequest);

        PotentialPartnerConditionResponse potentialPartnerConditionResponse = potentialPartnerConditionMapper.toPotentialPartnerConditionResponse(potentialPartnerCondition);
        try {
            potentialPartnerConditionRepository.save(potentialPartnerCondition);

            potentialPartnerConditionRequest.getPotentialPartnerActivityAreas().forEach(area ->{
                PotentialPartnerActivityArea potentialPartnerActivityArea = potentialPartnerActivityAreaMapper.toPotentialPartnerActivityArea(area);
                potentialPartnerActivityArea.setPotentialPartnerCondition(potentialPartnerCondition);

                potentialPartnerActivityAreaRepository.save(potentialPartnerActivityArea);
            });

            potentialPartnerConditionRequest.getPotentialPartnerConditionCountries().forEach(country ->{
                PotentialPartnerConditionCountry potentialPartnerConditionCountry = potentialPartnerConditionCountyMapper.toPotentialPartnerConditionCountry(country);
                potentialPartnerConditionCountry.setPotentialPartnerCondition(potentialPartnerCondition);

                potentialPartnerConditionCountryRepository.save(potentialPartnerConditionCountry);
            });

            potentialPartnerConditionRequest.getPotentialPartnerConditionProvinces().forEach(province ->{
                PotentialPartnerConditionProvince potentialPartnerConditionProvince = potentialPartnerConditionProvinceMapper.toPotentialPartnerConditionProvince(province);
                potentialPartnerConditionProvince.setPotentialPartnerCondition(potentialPartnerCondition);

                potentialPartnerConditionProvinceRepository.save(potentialPartnerConditionProvince);
            });
        } catch (Exception e) {
            throw new AppException(ErrorCode.UPDATE_POTENTIAL_PARTNER_CONDITION_ERROR);
        }
        return potentialPartnerConditionResponse;
    }

    private Company getCompany(){
        String accountId = SecurityContextHolder.getContext().getAuthentication().getName();
        return companyRepository.findByAccountId(accountId).orElseThrow();
    }
}
