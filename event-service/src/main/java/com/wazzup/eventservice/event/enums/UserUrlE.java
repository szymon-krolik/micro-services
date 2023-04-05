package com.wazzup.eventservice.event.enums;

import lombok.Getter;

public enum UserUrlE {
    GET_USER_BY_TOKEN("/user/by-token");

    @Getter
    private String url;

    UserUrlE(String url) {
        this.url = url;
    }
}
