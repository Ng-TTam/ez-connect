package com.example.easyconnect.repository;

import com.example.easyconnect.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("SELECT c FROM Company c WHERE c.accountInfo.id = :accountId")
    Optional<Company> findByAccountId(@Param("accountId") String accountId);
}