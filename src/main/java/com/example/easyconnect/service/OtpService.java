package com.example.easyconnect.service;

import com.example.easyconnect.exception.AppException;
import com.example.easyconnect.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    EmailService emailService;

    private static final String OTP_CHARACTERS = "0123456789";
    private static final int OTP_LENGTH = 6;
    private SecureRandom random = new SecureRandom();

    private static final int OTP_TTL_MIN = 3;

    public void send(String type, String email){
        try {
            String key = "OTP_" + type + "_" + email;
            String otp = generateOtp();

            stringRedisTemplate.opsForValue().set(key, otp);
            stringRedisTemplate.expire(key, OTP_TTL_MIN, TimeUnit.MINUTES);

            emailService.send(email,otp);
        }
        catch (Exception e){
            throw new AppException(ErrorCode.ERROR_SEND_OTP);
        }
    }

    public boolean verify(String type, String email, String otp){
        String key = "OTP_" + type+ "_" + email;
        String otpStored = stringRedisTemplate.opsForValue().get(key);
        if(otpStored == null)
            throw new AppException(ErrorCode.OTP_INVALID);
        if (otpStored.equals(otp)) {
            stringRedisTemplate.delete(key);
            return true;
        }
        return false;
    }

    private String generateOtp(){
        StringBuilder otp = new StringBuilder(OTP_LENGTH);
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARACTERS.charAt(random.nextInt(OTP_CHARACTERS.length())));
        }
        return otp.toString();
    }
}
