package com.example.easyconnect.entity;

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
public class PotentialPartnerConditionCountry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "potential_partner_id")
    PotentialPartnerCondition potentialPartnerCondition;

    @ManyToOne
    @JoinColumn(name = "county_id")
    Country country;
}
