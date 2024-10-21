package com.example.esayconnect.repository;

import com.example.esayconnect.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, String> {

    Optional<AccountInfo> findByEmail(String email);

    boolean existsByEmail(String email);
}
