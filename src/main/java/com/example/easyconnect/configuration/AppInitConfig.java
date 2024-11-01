package com.example.easyconnect.configuration;

import com.example.easyconnect.entity.AccountCmsInfo;
import com.example.easyconnect.repository.AccountCMSInfoRepository;
import com.example.easyconnect.enums.Role;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AppInitConfig {
    PasswordEncoder passwordEncoder;

    @NonFinal
    static String ADMIN_LOGIN = "admin";
    @NonFinal
    static String ADMIN_PASSWORD = "admin";

    @Bean
//    @ConditionalOnProperty(
//            prefix = "spring",
//            value = "datasource.driverClassName",
//            havingValue = "com.mysql.cj.jdbc.Driver")
    ApplicationRunner applicationRunner(AccountCMSInfoRepository accountCMSInfoRepository){
        return args -> {
            if(accountCMSInfoRepository.findByUsername(ADMIN_LOGIN).isEmpty()){

                AccountCmsInfo accountCmsInfo = AccountCmsInfo.builder()
                        .username(ADMIN_LOGIN)
                        .hashPassword(passwordEncoder.encode(ADMIN_PASSWORD))
                        .role(Role.ADMIN)
                        .build();

                accountCMSInfoRepository.save(accountCmsInfo);
            }
        };
    }
}
