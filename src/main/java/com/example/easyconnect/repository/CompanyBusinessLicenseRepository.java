package com.example.easyconnect.repository;

import com.example.easyconnect.entity.CompanyBusinessLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyBusinessLicenseRepository extends JpaRepository<CompanyBusinessLicense, String> {
}
