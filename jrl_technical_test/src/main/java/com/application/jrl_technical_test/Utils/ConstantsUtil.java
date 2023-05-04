package com.application.jrl_technical_test.Utils;

public enum ConstantsUtil {
    ACTIVE('A', "ACTIVE"),
    INACTIVE('I', "INACTIVE"),
    FEMALE('F', "FEMALE"),
    MALE('M', "MALE");

    private final Character value;

    private final String description;

    ConstantsUtil(Character value, String description) {
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
