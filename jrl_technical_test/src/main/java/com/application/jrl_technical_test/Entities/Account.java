package com.application.jrl_technical_test.Entities;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ACCOUNT", uniqueConstraints = @UniqueConstraint(columnNames = {"ID_ACCOUNT", "ACCOUNT_NUMBER"}))
public class Account implements java.io.Serializable{

    private String accountId;
    private String accountNumber;
    private String accountType;
    private BigDecimal initialBalance;
    private Character state;
    private Client client;
    private Set<Movement> movements;

    public Account(){}

    public Account(String accountId, String accountNumber, String accountType, BigDecimal initialBalance, Character state, Client client, Set<Movement> movements) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.state = state;
        this.client = client;
        this.movements = movements;
    }

    @Id
    @Column(name = "ID_ACCOUNT", unique = true, length = 36)
    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    @Column(name = "ACCOUNT_NUMBER", length = 20, unique = true)
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "ACCOUNT_TYPE", length = 15)
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Column(name = "INITIAL_BALANCE")
    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Column(name = "STATE", length = 1)
    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CLIENT")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    public Set<Movement> getMovements() {
        return movements;
    }

    public void setMovements(Set<Movement> movements) {
        this.movements = movements;
    }
}
