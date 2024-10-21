package com.example.esayconnect.entity;

import com.example.esayconnect.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SocialAccountInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "account_id")
    AccountInfo accountInfo;

    @Column(length = 64)
    String socialId;

    Type type;
}
