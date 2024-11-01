package com.example.easyconnect.entity;

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
public class ThanksLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

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

    long revenue;

    @OneToOne
    @JoinColumn(name = "currency_unit_id")
    MoneyUnitCategory currencyUnit;
}
