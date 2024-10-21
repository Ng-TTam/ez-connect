package com.example.esayconnect.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Representative {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    @Column(columnDefinition = "VARCHAR(100)")
    String name;

    @Column(columnDefinition = "VARCHAR(150)")
    String position;

    @Column(length = 50)
    String email;

    @Column(length = 50)
    String phone;

    @Column(length = 100)
    String zaloInfo;

    @Column(length = 100)
    String telegramInfo;

    @Column(length = 100)
    String whatsappInfo;

    @OneToOne
    @PrimaryKeyJoinColumn(name = "company_id")
    Company company;
}
