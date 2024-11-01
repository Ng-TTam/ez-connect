package com.example.easyconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Company {
    @Id
    @Column(length = 40, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(columnDefinition = "VARCHAR(100)")
    String name;

    @Column(length = 50)
    String website;

    @Column(columnDefinition = "VARCHAR(64)", unique = true)
    String taxCode;

    @OneToOne
    @JoinColumn(name="country_id")
    Country country;

    @OneToOne
    @JoinColumn(name = "province_id")
    Province province;

    @Column(length = 200)
    String address;

    int establishYear;
    int scale;
    long annualRevenue;

    @Column(length = 500)
    String outstandingPoint;

    int operatingYears;

    @Column(length = 100)
    String avatar;

    @Column(length = 100)
    String coverImage;

    @Column(columnDefinition = "VARCHAR(500)")
    String description;

    @Column(columnDefinition = "VARCHAR(500)")
    String vision;

    @Column(columnDefinition = "VARCHAR(500)")
    String primaryGoal;

    @Column(columnDefinition = "VARCHAR(500)")
    String coreValue;

    @Column(columnDefinition = "VARCHAR(500)")
    String businessRule;

    @Column(columnDefinition = "VARCHAR(500)")
    String achievements;

    boolean isVerify;

    @OneToOne
    @JoinColumn(name = "account_id", unique = true)
    AccountInfo accountInfo;
}
