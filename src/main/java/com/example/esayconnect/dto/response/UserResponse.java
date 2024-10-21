package com.example.esayconnect.dto.response;

import com.example.esayconnect.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    int id;
    String nameAccount;
    String username;
    String email;
    String number;
    LocalDate birth;
    String address;
    String gender;
    int rewardPoint;

    Role role;
}
