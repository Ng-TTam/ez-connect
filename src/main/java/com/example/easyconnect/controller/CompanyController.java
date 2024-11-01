package com.example.easyconnect.controller;

import com.example.easyconnect.dto.reqResp.CompanyDTO;
import com.example.easyconnect.dto.request.CompanyUpdateRequest;
import com.example.easyconnect.dto.request.PotentialPartnerConditionRequest;
import com.example.easyconnect.dto.request.RepresentativeUpdateRequest;
import com.example.easyconnect.dto.response.ApiResponse;
import com.example.easyconnect.dto.response.CompanyUpdateResponse;
import com.example.easyconnect.dto.response.PotentialPartnerConditionResponse;
import com.example.easyconnect.dto.response.RepresentativeUpdateResponse;
import com.example.easyconnect.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CompanyController {
    CompanyService companyService;

    @GetMapping
    ApiResponse<CompanyDTO> getCompanyByAccount(){
        return ApiResponse.<CompanyDTO>builder()
                .result(companyService.getCompanyByAccount())
                .build();
    }

    @PutMapping
    ApiResponse<CompanyUpdateResponse> updateCompanyInfo(
            @ModelAttribute @Valid CompanyUpdateRequest companyUpdateRequest){
        return ApiResponse.<CompanyUpdateResponse>builder()
                .result(companyService.updateCompanyInfo(companyUpdateRequest))
                .build();
    }

    @PutMapping("/representative")
    ApiResponse<RepresentativeUpdateResponse> updateRepresentative(
            @RequestBody @Valid RepresentativeUpdateRequest representativeUpdateRequest){
        return ApiResponse.<RepresentativeUpdateResponse>builder()
                .result(companyService.updateRepresentative(representativeUpdateRequest))
                .build();
    }

    @PutMapping("/potential-partner-condition")
    ApiResponse<PotentialPartnerConditionResponse> updatePotentialPartnerCondition(
            @RequestBody @Valid PotentialPartnerConditionRequest potentialPartnerConditionRequest){
        return ApiResponse.<PotentialPartnerConditionResponse>builder()
                .result(companyService.updatePotentialPartnerCondition(potentialPartnerConditionRequest))
                .build();
    }
}
