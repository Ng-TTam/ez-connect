package com.example.easyconnect.repository;

import com.example.easyconnect.entity.Company;
import com.example.easyconnect.entity.Representative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RepresentativeRepository extends JpaRepository<Representative, String> {
    Optional<Representative> findByCompany(Company company);
}
