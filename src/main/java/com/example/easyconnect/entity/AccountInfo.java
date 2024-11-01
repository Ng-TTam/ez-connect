package com.example.easyconnect.entity;

import com.example.easyconnect.enums.LoginType;
import com.example.easyconnect.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


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
