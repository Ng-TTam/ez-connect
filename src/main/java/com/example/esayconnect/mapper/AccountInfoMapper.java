package com.example.esayconnect.mapper;

import com.example.esayconnect.dto.request.AccountInfoCreationRequest;
import com.example.esayconnect.dto.response.AccountInfoResponse;
import com.example.esayconnect.entity.AccountInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountInfoMapper {

    AccountInfo toAccountInfo(AccountInfoCreationRequest accountInfoCreationRequest);

    AccountInfoResponse toAccountInfoResponse(AccountInfo accountInfo);
}
