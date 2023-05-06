package com.application.jrl_technical_test.Utils.Tasks;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.TimerTask;

public abstract class AppTask extends TimerTask {

    private String key = StringUtils.EMPTY;
    private Date date = new Date();
    private AppTaskType appTaskType;
    private AppTaskManagerService appTaskManagerService;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public AppTaskType getAppTaskType() {
        return appTaskType;
    }

    public void setAppTaskType(AppTaskType appTaskType) {
        this.appTaskType = appTaskType;
    }

    public void clearTask(String key){ this.appTaskManagerService.clearTask(key);}

    public void setManager(AppTaskManagerService appTaskManagerService){
        this.appTaskManagerService = appTaskManagerService;
    }

}
