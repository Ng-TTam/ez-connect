package com.example.easyconnect.repository;

import com.example.easyconnect.entity.AccountCmsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountCMSInfoRepository extends JpaRepository<AccountCmsInfo, String> {

    Optional<AccountCmsInfo> findByUsername(String username);
}
