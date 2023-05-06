package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Exception.InternalAppException;

public interface IAccountDayLimitService {

    void persistAccountDayLimit(String accountId) throws InternalAppException;
}
