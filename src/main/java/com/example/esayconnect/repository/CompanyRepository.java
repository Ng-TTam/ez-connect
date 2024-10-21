package com.example.esayconnect.repository;

import com.example.esayconnect.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {

    @Query("SELECT c FROM Company c WHERE c.accountInfo.id = :accountId")
    Company findByAccountId(@Param("accountId") String accountId);
}