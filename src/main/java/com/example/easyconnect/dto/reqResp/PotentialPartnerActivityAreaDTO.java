package com.example.easyconnect.dto.reqResp;

import com.example.easyconnect.entity.ActivityAreaCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PotentialPartnerActivityAreaDTO {
    int id;
    ActivityAreaCategory activityArea;
    boolean isPrimary;
}
