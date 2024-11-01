package com.example.easyconnect.entity;

import com.example.easyconnect.enums.Action;
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
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    Company sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    Company receiver;

    @Column(length = 100)
    String title;

    @Column(length = 500)
    String content;

    Status status;
    Action action;

    @Column(length = 500)
    String data;
}
