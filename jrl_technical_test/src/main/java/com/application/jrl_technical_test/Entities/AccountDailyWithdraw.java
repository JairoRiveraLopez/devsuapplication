package com.application.jrl_technical_test.Entities;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ACCOUNT_DAILY_WITHDRAW", uniqueConstraints = @UniqueConstraint(columnNames = "ID_ACCOUNT_DAILY_WITHDRAW"))
public class AccountDailyWithdraw {

    private String accountDailyWithdrawId;
    private Account account;
    private BigDecimal maxAmmount;

    public AccountDailyWithdraw(){}

    public AccountDailyWithdraw(String accountDailyWithdrawId, Account account, BigDecimal maxAmmount) {
        this.accountDailyWithdrawId = accountDailyWithdrawId;
        this.account = account;
        this.maxAmmount = maxAmmount;
    }

    @Id
    @Column(name = "ID_ACCOUNT_DAILY_WITHDRAW", unique = true, length = 36)
    public String getAccountDailyWithdrawId() {
        return accountDailyWithdrawId;
    }


    public void setAccountDailyWithdrawId(String accountDailyWithdrawId) {
        this.accountDailyWithdrawId = accountDailyWithdrawId;
    }

    @OneToOne
    @JoinColumn(name = "ID_ACCOUNT")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "MAX_AMMOUNT")
    public BigDecimal getMaxAmmount() {
        return maxAmmount;
    }

    public void setMaxAmmount(BigDecimal maxAmmount) {
        this.maxAmmount = maxAmmount;
    }
}
