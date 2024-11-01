package com.example.easyconnect.repository;

import com.example.easyconnect.entity.PotentialPartnerConditionProvince;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotentialPartnerConditionProvinceRepository extends JpaRepository<PotentialPartnerConditionProvince, Integer> {
}
