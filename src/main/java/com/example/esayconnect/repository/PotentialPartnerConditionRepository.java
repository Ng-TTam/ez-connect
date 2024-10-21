package com.example.esayconnect.repository;

import com.example.esayconnect.entity.Company;
import com.example.esayconnect.entity.PotentialPartnerCondition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PotentialPartnerConditionRepository extends JpaRepository<PotentialPartnerCondition, String> {
    List<PotentialPartnerCondition> findByCompany(Company company);
}
