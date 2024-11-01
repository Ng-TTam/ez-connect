package com.example.easyconnect.enums;

import lombok.Getter;

@Getter
public enum Action {
    SEEN("SEEN"),
    NOT_SEEN("NOT SEEN")
    ;

    Action(String name){
        this.name = name;
    };

    private final String name;
}
