package com.application.jrl_technical_test.Utils.Tasks;

public enum AppTaskType {
    DAILY_RETREAT_LIMIT("DAILY_RETREAT");

    private final String type;

    private AppTaskType(String type){this.type = type;}

    public String getType() {
        return type;
    }

    public static AppTaskType findType(String taskType){
        for(AppTaskType tt : values()){
            if(tt.getType().equals(taskType)){
                return tt;
            }
        }
        return null;
    }
}
