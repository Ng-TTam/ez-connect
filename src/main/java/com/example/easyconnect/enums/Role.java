package com.example.easyconnect.enums;

import lombok.Getter;

@Getter
public enum Role {
    USER("USER"),
    ADMIN("ADMIN")
    ;

    Role(String name){
        this.name = name;
    };

    private final String name;
}
