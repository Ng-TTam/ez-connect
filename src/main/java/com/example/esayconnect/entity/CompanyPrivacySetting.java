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
public class CompanyPrivacySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    boolean isPublicAvatar;
    boolean isPublicCoverImage;
    boolean isPublicDescription;
    boolean isPublicWebsite;
    boolean isPublicAddress;
    boolean isPublicScale;
    boolean isPublicRevenue;
    boolean isPublicActivityArea;
    boolean isPublicEstablishYear;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "company_id")
    Company company;
}
