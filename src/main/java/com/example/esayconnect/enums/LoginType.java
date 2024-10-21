package com.example.esayconnect.enums;

import lombok.Getter;

@Getter
public enum LoginType {
    LOGIN_SOCIAL("LOGIN_SOCIAL"),
    LOGIN("LOGIN"),
    ;

    LoginType(String name){
        this.name = name;
    };

    private final String name;
}
