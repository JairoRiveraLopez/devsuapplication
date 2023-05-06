package com.application.jrl_technical_test.Web.DTO;

import java.math.BigDecimal;
import java.util.Date;

public class MovementQueryDTO {

    private Date movementDate;
    private String movementType;
    private Double value;
    private Character state;
    private String accountNumberRelated;
    private BigDecimal availableBalance;

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    public String getAccountNumberRelated() {
        return accountNumberRelated;
    }

    public void setAccountNumberRelated(String accountNumberRelated) {
        this.accountNumberRelated = accountNumberRelated;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
