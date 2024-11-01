package com.example.easyconnect.enums;

import lombok.Getter;

@Getter
public enum Status {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE")
    ;

    Status(String status){
        this.status = status;
    };

    private final String status;
}
