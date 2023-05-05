package com.application.jrl_technical_test.Utils.Tasks;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;

@SpringBootApplication
@Stateless
public class AppTaskFactory {

    public AppTask getInstanceAppTask(AppTaskType appTaskType){
        AppTask appTaskInstance = null;
        switch (appTaskType){
            case DAILY_RETREAT_LIMIT:
                appTaskInstance = new AppTaskDailyLimit();
                break;
        }
        return appTaskInstance;
    }

}
