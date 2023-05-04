package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Account;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
@Stateless
public class AccountHome {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Account account){
        try{
            entityManager.persist(account);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public void remove(Account account){
        try{
            entityManager.remove(account);
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Account merge(Account account){
        try{
            Account result = entityManager.merge(account);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Account findById(String accountId){
        try{
            Account result = entityManager.find(Account.class, accountId);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

}
