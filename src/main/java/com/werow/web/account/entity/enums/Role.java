package com.werow.web.account.entity.enums;

public enum Role {

    GUEST(0), USER(1), FREELANCER(2), MANAGER(3), ADMIN(4);

    private final int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

//    GUEST, USER, FREELANCER, MANAGER, ADMIN
}
