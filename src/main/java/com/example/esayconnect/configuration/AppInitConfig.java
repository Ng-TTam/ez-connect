package com.example.esayconnect.configuration;

import com.example.esayconnect.entity.AccountCmsInfo;
import com.example.esayconnect.repository.AccountCMSInfoRepository;
import com.example.esayconnect.enums.Role;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AppInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;

    @NonFinal
    static String ADMIN_EMAIL_LOGIN = "admin@admin.com";
    @NonFinal
    static String ADMIN_PASSWORD = "admin";

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driverClassName",
            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(AccountCMSInfoRepository accountCMSInfoRepository){
        return args -> {
            if(accountCMSInfoRepository.findByUsername(ADMIN_EMAIL_LOGIN).isEmpty()){

                AccountCmsInfo accountCmsInfo = AccountCmsInfo.builder()
                        .username(ADMIN_EMAIL_LOGIN)
                        .hashPassword(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role(Role.ADMIN)
                        .build();

                accountCMSInfoRepository.save(accountCmsInfo);
            }
        };
    }
}
