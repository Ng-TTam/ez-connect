package com.example.esayconnect.entity;

import com.example.esayconnect.enums.LoginType;
import com.example.esayconnect.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AccountInfo {
    @Id
    @Column(length = 40)
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updateAt;

    @Column(length = 50)
    String email;

    @Column(length = 64)
    String hashPassword;

    LoginType loginType;

    @Column(length = 20)
    String language;

    Status status;
}
