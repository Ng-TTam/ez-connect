package com.example.esayconnect.dto.reqResp;

import com.example.esayconnect.entity.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyDTO {
    String id;
    String name;
    String website;
    String taxCode;
    CountryDTO country;
    ProvinceDTO province;
    String address;
    int establishYear;
    int scale;
    long annualRevenue;
    String outstandingPoint;
    int operatingYears;
    String avatar;
    String coverImage;
    String description;
    String vision;
    String primaryGoal;
    String coreValue;
    String businessRule;
    String achievements;
    boolean isVerify;
    AccountInfoDTO accountInfo;

    RepresentativeDTO representative;
    CompanyBusinessLicenseDTO companyBusinessLicense;
    List<PotentialPartnerConditionDTO> potentialPartnerConditions;
}
