package com.example.easyconnect.repository;

import com.example.easyconnect.entity.PotentialPartnerActivityArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PotentialPartnerActivityAreaRepository extends JpaRepository<PotentialPartnerActivityArea, Integer> {
}
