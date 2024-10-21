package com.example.esayconnect.repository;

import com.example.esayconnect.entity.AccountCmsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountCMSInfoRepository extends JpaRepository<AccountCmsInfo, String> {

    Optional<AccountCmsInfo> findByUsername(String username);
}
