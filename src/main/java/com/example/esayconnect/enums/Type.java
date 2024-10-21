package com.example.esayconnect.enums;

import lombok.Getter;

@Getter
public enum Type {
    FACEBOOK("FACEBOOK"),
    GOOGLE("GOOGLE"),
    ;

    Type(String name){
        this.name = name;
    };

    private final String name;
}
