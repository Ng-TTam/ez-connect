package com.example.easyconnect.repository;

import com.example.easyconnect.entity.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, String> {

    Optional<AccountInfo> findByEmail(String email);

    boolean existsByEmail(String email);
}
