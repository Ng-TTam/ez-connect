package com.example.esayconnect.controller;

import com.example.esayconnect.dto.request.*;
import com.example.esayconnect.dto.response.*;
import com.example.esayconnect.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest authenticationRequest){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticate(authenticationRequest))
                .build();
    }

    @PostMapping("/cms/login")
    ApiResponse<AuthenticationResponse> loginCms(@RequestBody AuthenticationCmsRequest authenticationCmsRequest){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.authenticateCms(authenticationCmsRequest))
                .build();
    }

    @PostMapping("/send-otp")
    ApiResponse<String> sendOtp(@RequestBody @Valid AccountInfoCreationRequest accountInfoCreationRequest){
        authenticationService.otpSignUp(accountInfoCreationRequest);
        return ApiResponse.<String>builder()
                .result("Send otp successful")
                .build();
    }

    @PostMapping("/sign-up")
    ApiResponse<AuthenticationResponse> signUp(@RequestBody @Valid AccountInfoCreationRequest accountInfoCreationRequest,
                                            @RequestParam String otp){
        return ApiResponse.<AuthenticationResponse>builder()
                .result(authenticationService.signUp(accountInfoCreationRequest, otp))
                .build();
    }

    @PostMapping("/send-reset-otp")
    ApiResponse<String> sendResetPasswordOtp(@RequestBody AuthResetPassRequest authResetPassRequest){
        authenticationService.otpResetPassword(authResetPassRequest);
        return ApiResponse.<String>builder()
                .result("Send otp successful")
                .build();
    }

    @PostMapping("/verify-reset-otp")
    ApiResponse<String> verifyResetPasswordOtp(@RequestBody AuthResetPassRequest authResetPassRequest,
                                                @RequestParam String otp){
        return ApiResponse.<String>builder()
                .result(authenticationService.verifyOtpResetPassword(authResetPassRequest, otp))
                .build();
    }

    @PostMapping("/forgot-password")
    ApiResponse<Boolean> resetPassword(@RequestBody AuthResetPassRequest authResetPassRequest,
                                       @RequestParam String key){
        return ApiResponse.<Boolean>builder()
                .result(authenticationService.resetPassword(authResetPassRequest, key))
                .build();
    }

    @PostMapping("/verify")
    ApiResponse<VerifyTokenResponse> verify(@RequestBody VerifyTokenRequest verifyTokenRequest)
            throws JOSEException, ParseException{
        return ApiResponse.<VerifyTokenResponse>builder()
                .result(authenticationService.verify(verifyTokenRequest))
                .build();
    }

    @PostMapping("/refresh-token")
    ApiResponse<TokenResponse> refreshToken(@RequestBody RefreshRequest refreshRequest)
            throws JOSEException, ParseException{
        return ApiResponse.<TokenResponse>builder()
                .result(authenticationService.refreshToken(refreshRequest))
                .build();
    }

    @PostMapping("/log-out")
    ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) throws ParseException, JOSEException{
        authenticationService.logout(logoutRequest);
        return ResponseEntity.ok("Log out successful");
    }

}
