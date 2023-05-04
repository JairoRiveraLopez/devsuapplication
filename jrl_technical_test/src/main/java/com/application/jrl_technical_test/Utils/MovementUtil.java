package com.application.jrl_technical_test.Utils;

public enum MovementUtil {
    SUCCESS('S', "SUCCESSFUL"),
    FAILED('F', "FAILED"),
    PENDING('P', "PENDING"),
    REJECTED('R', "REJECTED");

    private final Character value;

    private final String description;

    MovementUtil(Character value, String description) {
        this.value = value;
        this.description = description;
    }

    public Character getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
