package com.application.jrl_technical_test.Utils.Tasks.Beans;

import com.application.jrl_technical_test.DAO.AccountDailyWithdrawHome;
import com.application.jrl_technical_test.Entities.AccountDailyWithdraw;
import com.application.jrl_technical_test.Utils.ConstantsUtil;
import com.application.jrl_technical_test.Utils.FormatUtil;
import com.application.jrl_technical_test.Utils.Tasks.AppTaskManagerService;
import com.application.jrl_technical_test.Utils.Tasks.AppTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class AppTaskDailyLimitBean {

    @Autowired
    private AccountDailyWithdrawHome accountDailyWithdrawHome;

    @Autowired
    private AppTaskManagerService appTaskManagerService;


    @Transactional
    public void restartDailyLimitWithdraw(String key) throws Exception{
        try{
            List<AccountDailyWithdraw> accountDailyWithdrawList = accountDailyWithdrawHome.findAll();
            if(!accountDailyWithdrawList.isEmpty()){
                for(AccountDailyWithdraw adl : accountDailyWithdrawList){
                    adl.setMaxAmmount(new BigDecimal("1000"));
                    accountDailyWithdrawHome.merge(adl);
                }
                appTaskManagerService.clearTask(key);
                appTaskManagerService.addTask(ConstantsUtil.KEY_DAILY_LIMIT_APP_TASK, FormatUtil.addSubstractDaysDate(new Date(), 1), AppTaskType.DAILY_RETREAT_MAX_AMMOUNT);
            }else {
                appTaskManagerService.clearTask(key);
                appTaskManagerService.addTask(ConstantsUtil.KEY_DAILY_LIMIT_APP_TASK, FormatUtil.addSubstractDaysDate(new Date(), 1), AppTaskType.DAILY_RETREAT_MAX_AMMOUNT);
            }
        }catch (Exception e){
            throw e;
        }
    }


}
