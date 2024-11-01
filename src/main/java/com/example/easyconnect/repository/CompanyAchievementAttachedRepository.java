package com.example.easyconnect.repository;

import com.example.easyconnect.entity.CompanyAchievementAttached;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyAchievementAttachedRepository extends JpaRepository<CompanyAchievementAttached, String> {
}
