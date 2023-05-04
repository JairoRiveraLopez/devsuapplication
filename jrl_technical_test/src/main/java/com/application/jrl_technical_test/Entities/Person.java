package com.application.jrl_technical_test.Entities;

import javax.persistence.*;

@MappedSuperclass
public abstract class Person implements java.io.Serializable {

    private String personId;
    private String identification;
    private String name;
    private String lastName1;
    private String lastName2;
    private Character genre;
    private Integer age;
    private String address;
    private String phone;

    public Person(String personId, String identification, String name, String lastName1, String lastName2, Character genre, Integer age, String address, String phone) {
        this.personId = personId;
        this.identification = identification;
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
        this.genre = genre;
        this.age = age;
        this.address = address;
        this.phone = phone;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

    public Character getGenre() {
        return genre;
    }

    public void setGenre(Character genre) {
        this.genre = genre;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
