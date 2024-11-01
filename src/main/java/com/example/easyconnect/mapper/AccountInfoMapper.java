package com.example.easyconnect.mapper;

import com.example.easyconnect.dto.request.AccountInfoCreationRequest;
import com.example.easyconnect.dto.response.AccountInfoResponse;
import com.example.easyconnect.entity.AccountInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountInfoMapper {

    AccountInfo toAccountInfo(AccountInfoCreationRequest accountInfoCreationRequest);

    AccountInfoResponse toAccountInfoResponse(AccountInfo accountInfo);
}
