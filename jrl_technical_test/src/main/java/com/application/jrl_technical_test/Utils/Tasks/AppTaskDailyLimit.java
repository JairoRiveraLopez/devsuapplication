package com.application.jrl_technical_test.Utils.Tasks;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppTaskDailyLimit extends AppTask{

    public AppTaskDailyLimit(){
        setAppTaskType(AppTaskType.DAILY_RETREAT_LIMIT);
    }

    @Override
    public void run() {

    }
}
