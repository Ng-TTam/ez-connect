package com.example.easyconnect.dto.response;

import com.example.easyconnect.dto.reqResp.CountryDTO;
import com.example.easyconnect.dto.reqResp.ProvinceDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyUpdateResponse {
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
}
