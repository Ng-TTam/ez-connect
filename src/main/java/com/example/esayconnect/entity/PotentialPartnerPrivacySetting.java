package com.example.esayconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PotentialPartnerPrivacySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    boolean isPublicScale;
    boolean isPublicActivityArea;
    boolean isPublicRevenue;
    boolean isPublicAddress;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "company_id")
    Company company;
}
