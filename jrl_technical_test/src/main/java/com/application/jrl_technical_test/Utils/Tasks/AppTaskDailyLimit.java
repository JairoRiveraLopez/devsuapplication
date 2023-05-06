package com.application.jrl_technical_test.Utils.Tasks;

import com.application.jrl_technical_test.Utils.Tasks.Beans.AppTaskDailyLimitBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppTaskDailyLimit extends AppTask{

    @Autowired
    private AppTaskDailyLimitBean appTaskDailyLimitBean;

    public AppTaskDailyLimit(){
        setAppTaskType(AppTaskType.DAILY_RETREAT_MAX_AMMOUNT);
    }

    @Override
    public void run() {
        try{
            appTaskDailyLimitBean.restartDailyLimitWithdraw(getKey());
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
