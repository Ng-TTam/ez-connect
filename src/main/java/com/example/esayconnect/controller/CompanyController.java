package com.example.esayconnect.controller;

import com.example.esayconnect.dto.reqResp.CompanyDTO;
import com.example.esayconnect.dto.response.ApiResponse;
import com.example.esayconnect.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    CompanyService companyService;

    @GetMapping
    ApiResponse<CompanyDTO> getCompanyByAccount(){
        return ApiResponse.<CompanyDTO>builder()
                .result(companyService.getCompanyByAccount())
                .build();
    }
}
