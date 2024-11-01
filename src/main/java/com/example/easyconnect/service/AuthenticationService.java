package com.example.easyconnect.service;

import com.example.easyconnect.dto.request.*;
import com.example.easyconnect.dto.response.AuthenticationResponse;
import com.example.easyconnect.dto.response.TokenResponse;
import com.example.easyconnect.dto.response.VerifyTokenResponse;
import com.example.easyconnect.entity.*;
import com.example.easyconnect.enums.LoginType;
import com.example.easyconnect.enums.Role;
import com.example.easyconnect.enums.Status;
import com.example.easyconnect.exception.AppException;
import com.example.easyconnect.exception.ErrorCode;
import com.example.easyconnect.repository.*;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.experimental.NonFinal;
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
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
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

    @NonFinal
    @Value("${jwt.secret}")
    protected String jwtSecret;

    @NonFinal
    @Value("${jwt.expiration}")
    protected int jwtExpiration;

    @NonFinal
    @Value("${jwt.refreshable}")
    protected int jwtRefreshable;

    static final int TTL_TOKEN_RESET_CACHE = 5;
    static final String PRE_RESET_PASS = "TOKEN_RESET_PASS_";
    static final String PRE_TOKEN = "TOKEN_";
    static final String PRE_REFRESH_TOKEN = "REFRESH_TOKEN_";

    public AuthenticationResponse authenticateCms(AuthenticationCmsRequest authenticationCmsRequest){
        AccountCmsInfo accountCmsInfo = accountCMSInfoRepository.findByUsername(authenticationCmsRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));

        boolean authenticated = passwordEncoder.matches(authenticationCmsRequest.getPassword(), accountCmsInfo.getHashPassword());
        if(!authenticated)
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        String token = createSignedToken(accountCmsInfo.getId(), accountCmsInfo.getRole()).serialize();
        String refreshToken = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(PRE_TOKEN + token, String.valueOf(accountCmsInfo.getId()));
        stringRedisTemplate.expire(PRE_TOKEN + token, Duration.ofDays(jwtExpiration));
        stringRedisTemplate.opsForValue().set(PRE_RESET_PASS + refreshToken, String.valueOf(accountCmsInfo.getId()));
        stringRedisTemplate.expire(PRE_RESET_PASS + refreshToken, Duration.ofDays(jwtRefreshable));

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

        return createToken(accountInfo);
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
            stringRedisTemplate.opsForValue().set(PRE_RESET_PASS + key, account.getId());
            stringRedisTemplate.expire(PRE_RESET_PASS + key, Duration.ofMinutes(TTL_TOKEN_RESET_CACHE));
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
        String accountId = stringRedisTemplate.opsForValue().get(PRE_RESET_PASS + key);

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

    private JWSObject createSignedToken(String subject, Role scope) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        JWTClaimsSet.Builder claimsBuilder = new JWTClaimsSet.Builder()
                .subject(subject)
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(jwtExpiration, ChronoUnit.DAYS)));

        if (scope != null) {
            claimsBuilder.claim("scope", scope);
        }

        JWTClaimsSet jwtClaimsSet = claimsBuilder.build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(jwtSecret.getBytes()));
        } catch (JOSEException e) {
            throw new AppException(ErrorCode.INTERNAL_ERROR);
        }

        return jwsObject;
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

    public TokenResponse refreshToken(RefreshRequest refreshRequest){
        String userId = stringRedisTemplate.opsForValue().get(PRE_REFRESH_TOKEN + refreshRequest.getRefreshToken());

        if(userId == null){
            throw new AppException(ErrorCode.EXPIRED_TOKEN);
        }
        var account = accountCMSInfoRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        String token = createSignedToken(account.getId(), account.getRole()).serialize();

        stringRedisTemplate.opsForValue().set(PRE_TOKEN + token, userId);
        stringRedisTemplate.expire(PRE_TOKEN + token, Duration.ofDays(jwtExpiration));

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
        String token = createSignedToken(accountInfo.getId(), null).serialize();
        String refreshToken = UUID.randomUUID().toString();

        stringRedisTemplate.opsForValue().set(PRE_TOKEN + token, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(PRE_TOKEN + token, Duration.ofDays(jwtExpiration));
        stringRedisTemplate.opsForValue().set(PRE_REFRESH_TOKEN + refreshToken, String.valueOf(accountInfo.getId()));
        stringRedisTemplate.expire(PRE_REFRESH_TOKEN + refreshToken, Duration.ofDays(jwtRefreshable));

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }
}
