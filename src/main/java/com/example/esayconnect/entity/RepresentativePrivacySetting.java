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
public class RepresentativePrivacySetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    boolean isPublicName;
    boolean isPublicEmail;
    boolean isPublicPhone;
    boolean isPublicZaloInfo;
    boolean isPublicTelegramInfo;
    boolean isPublicWhatsappInfo;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "company_id")
    Company company;
}
