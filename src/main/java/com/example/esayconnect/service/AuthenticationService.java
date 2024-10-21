package com.example.esayconnect.service;

import com.example.esayconnect.dto.request.*;
import com.example.esayconnect.dto.response.AuthenticationResponse;
import com.example.esayconnect.dto.response.TokenResponse;
import com.example.esayconnect.dto.response.VerifyTokenResponse;
import com.example.esayconnect.entity.*;
import com.example.esayconnect.enums.LoginType;
import com.example.esayconnect.enums.Status;
import com.example.esayconnect.exception.AppException;
import com.example.esayconnect.exception.ErrorCode;
import com.example.esayconnect.repository.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class AuthenticationService {
    @Autowired
    AccountCMSInfoRepository accountCMSInfoRepository;
    @Autowired
    AccountInfoRepository accountInfoRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    RepresentativeRepository representativeRepository;
    @Autowired
    PotentialPartnerConditionRepository potentialPartnerConditionRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    OtpService otpService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    @Value("${jwt.refreshable}")
    private int jwtRefreshable;

    private static final int TTL_TOKEN_RESET_CACHE = 5;

    public AuthenticationResponse authenticateCms(AuthenticationCmsRequest authenticationRequest){
        AccountCmsInfo accountCmsInfo = accountCMSInfoRepository.findByUsername(authenticationRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), accountCmsInfo.getHashPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = generateToken(accountCmsInfo);
        String refreshToken = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(token, String.valueOf(accountCmsInfo.getId()));
        stringRedisTemplate.expire(token, Duration.ofDays(jwtExpiration));
        stringRedisTemplate.opsForValue().set(refreshToken, String.valueOf(accountCmsInfo.getId()));
        stringRedisTemplate.expire(refreshToken, Duration.ofDays(jwtRefreshable));

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        AccountInfo accountInfo = accountInfoRepository.findByEmail(authenticationRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), accountInfo.getHashPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = generateToken(accountInfo);
        String refreshToken = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(token, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(token, Duration.ofDays(jwtExpiration));
        stringRedisTemplate.opsForValue().set(refreshToken, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(refreshToken, Duration.ofDays(jwtRefreshable));

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    public void otpSignUp(AccountInfoCreationRequest accountInfoCreationRequest){
        if(accountInfoRepository.existsByEmail(accountInfoCreationRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        otpService.send("SIGNUP", accountInfoCreationRequest.getEmail());
    }

    public void otpResetPassword(AuthResetPassRequest authResetPassRequest){
        var account = accountInfoRepository.findByEmail(authResetPassRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        if(!account.getLoginType().equals(LoginType.LOGIN))
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);

        otpService.send("RESET", account.getEmail());
    }

    public String verifyOtpResetPassword(AuthResetPassRequest authResetPassRequest, String otp){
        if(!otpService.verify("RESET", authResetPassRequest.getEmail(), otp))
            throw new AppException(ErrorCode.OTP_INVALID);

        var account = accountInfoRepository.findByEmail(authResetPassRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        if(!account.getLoginType().equals(LoginType.LOGIN))
            throw new AppException(ErrorCode.EMAIL_NOT_EXISTED);

        String key = UUID.randomUUID().toString();
        try{
            stringRedisTemplate.opsForValue().set("TOKEN_RESET_PASS_" + key, account.getId());
            stringRedisTemplate.expire("TOKEN_RESET_PASS_" + key, Duration.ofMinutes(TTL_TOKEN_RESET_CACHE));
            return key;
        } catch (Exception e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }
    }

    @Transactional
    public AuthenticationResponse signUp(AccountInfoCreationRequest accountInfoCreationRequest, String otp){
        if(accountInfoRepository.existsByEmail(accountInfoCreationRequest.getEmail()))
            throw new AppException(ErrorCode.EMAIL_EXISTED);

        if(!otpService.verify("SIGNUP", accountInfoCreationRequest.getEmail(), otp))
            throw new AppException(ErrorCode.OTP_INVALID);

        AccountInfo account = AccountInfo.builder()
                .email(accountInfoCreationRequest.getEmail())
                .hashPassword(passwordEncoder.encode(accountInfoCreationRequest.getHashPassword()))
                .status(Status.ACTIVE)
                .language("English")
                .loginType(LoginType.LOGIN)
                .build();

        try {
            accountInfoRepository.save(account);

            Company company = Company.builder()
                    .accountInfo(account)
                    .build();
            companyRepository.save(company);

            Representative representative = Representative.builder()
                    .company(company)
                    .build();
            representativeRepository.save(representative);

            PotentialPartnerCondition potentialPartnerCondition = PotentialPartnerCondition.builder()
                    .company(company)
                    .build();
            potentialPartnerConditionRepository.save(potentialPartnerCondition);
        } catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }

        return createToken(account);
    }

    @Transactional
    public boolean resetPassword(AuthResetPassRequest authChangePassRequest, String key){
        String accountId = stringRedisTemplate.opsForValue().get("TOKEN_RESET_PASS_" + key);

        if(accountId == null)
            throw new AppException(ErrorCode.KEY_NOT_EXISTED);

        var account = accountInfoRepository.findById(accountId)
                .orElseThrow(() -> new AppException(ErrorCode.EMAIL_NOT_EXISTED));

        account.setHashPassword(passwordEncoder.encode(authChangePassRequest.getNewPassword()));
        try {
            accountInfoRepository.save(account);
        } catch (DataIntegrityViolationException e){
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }
        return true;
    }

    private String generateToken(AccountCmsInfo accountCmsInfo){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountCmsInfo.getUsername())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(jwtExpiration, ChronoUnit.DAYS).toEpochMilli()))
                .claim("scope", accountCmsInfo.getRole())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateToken(AccountInfo accountInfo){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(accountInfo.getId())
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now()
                        .plus(jwtExpiration, ChronoUnit.DAYS).toEpochMilli()))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    public VerifyTokenResponse verify(VerifyTokenRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;

        try {
            verifyToken(token, false);
        } catch (RuntimeException e) {
            isValid = false;
        }

        return VerifyTokenResponse.builder().valid(isValid).build();
    }

    public TokenResponse refreshToken(RefreshRequest refreshRequest) throws ParseException, JOSEException {
        String userId = stringRedisTemplate.opsForValue().get(refreshRequest.getRefreshToken());

        if(userId == null){
            throw new RuntimeException("Please login again");
        }
        var account = accountCMSInfoRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        String token = generateToken(account);

        stringRedisTemplate.opsForValue().set(token, userId);
        stringRedisTemplate.expire(token, Duration.ofDays(jwtExpiration));

        return TokenResponse.builder().token(token).build();
    }

    public SignedJWT verifyToken(String token, boolean isRefresh) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = (isRefresh)
                ? new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                .toInstant().plus(jwtRefreshable, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    //need update
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        try {
            stringRedisTemplate.delete(request.getToken());
        } catch (RuntimeException e){
            log.info("Token already expired");
        }
    }

    private AuthenticationResponse createToken(AccountInfo accountInfo){
        String token = generateToken(accountInfo);
        String refreshToken = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(token, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(token, Duration.ofDays(jwtExpiration));
        stringRedisTemplate.opsForValue().set(refreshToken, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(refreshToken, Duration.ofDays(jwtRefreshable));

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
