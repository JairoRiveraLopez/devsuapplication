package com.application.jrl_technical_test.Utils;

public enum AccountUtil {

    CURRENT("CURRENT"),
    SAVINGS("SAVINGS"),
    CREDIT("CREDIT");

    private final String value;

    AccountUtil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
