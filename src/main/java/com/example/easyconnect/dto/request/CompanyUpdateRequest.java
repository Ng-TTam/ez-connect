package com.example.easyconnect.dto.request;

import com.example.easyconnect.dto.reqResp.CountryDTO;
import com.example.easyconnect.dto.reqResp.ProvinceDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CompanyUpdateRequest {
    @NotBlank
    String name;

    @NotBlank
    String website;

    @NotBlank
    String taxCode;

//    @NotNull
    CountryDTO country;

//    @NotNull
    ProvinceDTO province;

    String address;

    @NotNull
    int establishYear;

    @NotNull
    int scale;

    @NotNull
    long annualRevenue;
    String outstandingPoint;
    int operatingYears;

    String description;

    @NotBlank
    String vision;

    @NotBlank
    String primaryGoal;

    @NotBlank
    String coreValue;

    @NotBlank
    String businessRule;

    @NotBlank
    String achievements;

    boolean isVerify;

    MultipartFile avatar;

    MultipartFile coverImage;

    @NotNull
    MultipartFile businessLicense;

    @NotNull
    MultipartFile achievementAttached;
}
