package com.application.jrl_technical_test.Utils;

public enum MovementUtil {
    SUCCESS('S', "SUCCESSFUL"),
    FAILED('F', "FAILED"),
    PENDING('P', "PENDING"),
    REJECTED('R', "REJECTED"),
    WITHDRAW('W', "WITHDRAW"),
    DEPOSIT('D', "DEPOSIT");

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

    public static Character getValueByDescription(String description) {
        for (MovementUtil option : MovementUtil.values()) {
            if (option.getDescription().equalsIgnoreCase(description)) {
                return option.getValue();
            }
        }
        return 'X';
    }

    public static String getDescriptionByValue(Character value) {
        for (MovementUtil option : MovementUtil.values()) {
            if (option.getValue().equals(value)) {
                return option.getDescription();
            }
        }
        return "X";
    }
}
