package com.example.esayconnect.repository;

import com.example.esayconnect.entity.Company;
import com.example.esayconnect.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, String> {
    Optional<Representative> findByCompany(Company company);
}
