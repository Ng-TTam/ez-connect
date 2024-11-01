package com.example.easyconnect.repository;

import com.example.easyconnect.entity.PotentialPartnerConditionCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotentialPartnerConditionCountryRepository extends JpaRepository<PotentialPartnerConditionCountry, Integer> {
}
