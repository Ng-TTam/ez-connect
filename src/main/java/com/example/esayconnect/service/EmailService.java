package com.example.esayconnect.service;

import com.example.esayconnect.exception.ErrorCode;
import com.example.esayconnect.exception.AppException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void send(String toEmail, String otp) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(toEmail);
            mimeMessageHelper.setSubject("Your code");
            mimeMessageHelper.setText("Your OTP is: " + otp);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new AppException(ErrorCode.EMAIL_NOT_EXIST);
        }
    }
}
