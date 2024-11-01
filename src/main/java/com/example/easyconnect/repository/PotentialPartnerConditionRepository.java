package com.example.easyconnect.repository;

import com.example.easyconnect.entity.Company;
import com.example.easyconnect.entity.PotentialPartnerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PotentialPartnerConditionRepository extends JpaRepository<PotentialPartnerCondition, String> {
    Optional<PotentialPartnerCondition> findByCompany(Company company);
}
