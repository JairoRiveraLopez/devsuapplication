package com.application.jrl_technical_test.Utils.Tasks;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import javax.ejb.Stateless;

@SpringBootApplication
@Stateless
public class AppTaskFactory {

    @Bean
    @Scope("prototype")
    public AppTask getInstanceAppTask(AppTaskType appTaskType){
        AppTask appTaskInstance = null;
        switch (appTaskType){
            case DAILY_RETREAT_MAX_AMMOUNT:
                appTaskInstance = new AppTaskDailyLimit();
                break;
        }
        return appTaskInstance;
    }

}
