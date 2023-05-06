package com.application.jrl_technical_test.Web.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class ReportAccountStateDTO {

    private Date movementDate;
    private String clientName;
    private String accountNumber;
    private String accountType;
    private BigDecimal actualBalance;
    private String movementState;
    private String movementType;
    private BigDecimal movementValue;

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getActualBalance() {
        return actualBalance;
    }

    public void setActualBalance(BigDecimal actualBalance) {
        this.actualBalance = actualBalance;
    }

    public String getMovementState() {
        return movementState;
    }

    public void setMovementState(String movementState) {
        this.movementState = movementState;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public BigDecimal getMovementValue() {
        return movementValue;
    }

    public void setMovementValue(BigDecimal movementValue) {
        this.movementValue = movementValue;
    }
}
