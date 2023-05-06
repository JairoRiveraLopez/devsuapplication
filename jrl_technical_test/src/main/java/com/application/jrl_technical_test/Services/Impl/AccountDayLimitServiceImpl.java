package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.AccountDailyWithdrawHome;
import com.application.jrl_technical_test.DAO.AccountHome;
import com.application.jrl_technical_test.DAO.AppProgrammedTaskHome;
import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.AccountDailyWithdraw;
import com.application.jrl_technical_test.Entities.AppProgrammedTask;
import com.application.jrl_technical_test.Services.IServices.IAccountDayLimitService;
import com.application.jrl_technical_test.Utils.ConstantsUtil;
import com.application.jrl_technical_test.Utils.FormatUtil;
import com.application.jrl_technical_test.Utils.Tasks.AppTaskManagerService;
import com.application.jrl_technical_test.Utils.Tasks.AppTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountDayLimitServiceImpl implements IAccountDayLimitService {

    @Autowired
    private AccountHome accountHome;

    @Autowired
    private AccountDailyWithdrawHome accountDailyWithdrawHome;

    @Autowired
    private AppProgrammedTaskHome appProgrammedTaskHome;

    @Autowired
    private AppTaskManagerService appTaskManagerService;

    @Override
    public void persistAccountDayLimit(String accountId) {
        try{
            Optional<Account> accountQuery = Optional.ofNullable(accountHome.findById(accountId));
            if(accountQuery.isPresent()){
                AccountDailyWithdraw accountDailyWithdraw = new AccountDailyWithdraw();
                accountDailyWithdraw.setAccountDailyWithdrawId(UUID.randomUUID().toString());
                accountDailyWithdraw.setMaxAmmount(new BigDecimal("1000"));
                accountDailyWithdraw.setAccount(accountQuery.get());
                accountDailyWithdrawHome.persist(accountDailyWithdraw);

                initializeProgrammedTaskIfFirst();
            }
        }catch (Exception e){
            throw e;
        }
    }

    public void initializeProgrammedTaskIfFirst(){
        try{
            Optional<AppProgrammedTask> appProgrammedTask = Optional.ofNullable
                    (appProgrammedTaskHome.findByCode(ConstantsUtil.KEY_DAILY_LIMIT_APP_TASK));
            if(!appProgrammedTask.isPresent()) {
                appTaskManagerService.addTask(ConstantsUtil.KEY_DAILY_LIMIT_APP_TASK,
                        FormatUtil.addSubstractDaysDate(FormatUtil.getCleanTodayDate(), 1), AppTaskType.DAILY_RETREAT_MAX_AMMOUNT);
            }
        }catch (Exception e){
            throw e;
        }
    }
}
