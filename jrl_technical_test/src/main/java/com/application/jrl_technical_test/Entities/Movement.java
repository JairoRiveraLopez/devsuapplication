package com.application.jrl_technical_test.Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "MOVEMENT", uniqueConstraints = @UniqueConstraint(columnNames = "ID_MOVEMENT"))
public class Movement implements java.io.Serializable{

    private String idMovement;
    private Date movementDate;
    private String movementType;
    private BigDecimal value;
    private Character state;
    private Account account;

    private BigDecimal availableBalance;

    public Movement(){}

    public Movement(String idMovement, Date movementDate, String movementType, BigDecimal value, Character state, Account account, BigDecimal availableBalance) {
        this.idMovement = idMovement;
        this.movementDate = movementDate;
        this.movementType = movementType;
        this.value = value;
        this.state = state;
        this.account = account;
        this.availableBalance = availableBalance;
    }

    @Id
    @Column(name = "ID_MOVEMENT", unique = true, length = 36)
    public String getIdMovement() {
        return idMovement;
    }

    public void setIdMovement(String idMovement) {
        this.idMovement = idMovement;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "MOVEMENT_DATE", length = 10)
    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    @Column(name = "MOVEMENT_TYPE", length = 15)
    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    @Column(name = "VALUE")
    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Column(name = "STATE")
    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ACCOUNT")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "AVAILABLE_BALANCE")
    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}
