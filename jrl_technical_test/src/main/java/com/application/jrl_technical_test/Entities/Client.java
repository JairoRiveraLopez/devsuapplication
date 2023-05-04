package com.application.jrl_technical_test.Entities;

import javax.persistence.*;

import java.util.Set;

@Entity
@Table(name = "CLIENT", uniqueConstraints = @UniqueConstraint(columnNames = "ID_CLIENT"))
public class Client extends Person implements java.io.Serializable{

    private String clientId;

    private String password;
    private Character state;
    private Set<Account> accounts;

    public Client() {}

    public Client(String personId, String identification, String name, String lastName1, String lastName2, Character genre, Integer age, String address, String phone, String clientId, String password, Character state, Set<Account> accounts) {
        super(personId, identification, name, lastName1, lastName2, genre, age, address, phone);
        this.clientId = clientId;
        this.password = password;
        this.state = state;
        this.accounts = accounts;
    }

    @Id
    @Column(name = "ID_CLIENT", unique = true, length = 36)
    public String getClientId() {
        return clientId;
    }

    @Column(name = "PASSWORD", length = 60)
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "STATE", length = 1)
    public Character getState() {
        return state;
    }

    public void setState(Character state) {
        this.state = state;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
