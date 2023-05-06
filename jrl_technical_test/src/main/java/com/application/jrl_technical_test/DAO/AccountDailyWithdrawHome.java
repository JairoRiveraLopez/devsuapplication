package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.AccountDailyWithdraw;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SpringBootApplication
@Stateless
public class AccountDailyWithdrawHome {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(AccountDailyWithdraw accountDailyWithdraw){
        try{
            entityManager.persist(accountDailyWithdraw);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public void remove(AccountDailyWithdraw accountDailyWithdraw){
        try{
            entityManager.remove(accountDailyWithdraw);
        } catch (RuntimeException error){
            throw error;
        }
    }

    public AccountDailyWithdraw merge(AccountDailyWithdraw accountDailyWithdraw){
        try{
            AccountDailyWithdraw result = entityManager.merge(accountDailyWithdraw);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public List<AccountDailyWithdraw> findAll(){
        try{
            TypedQuery<AccountDailyWithdraw> query = entityManager.createQuery("SELECT DW FROM AccountDailyWithdraw DW", AccountDailyWithdraw.class);
            List<AccountDailyWithdraw> accountDailyWithdrawList = query.getResultList();
            return accountDailyWithdrawList;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public AccountDailyWithdraw findByAccountId(String accountId){
        try{
            TypedQuery<AccountDailyWithdraw> query = entityManager.createQuery("SELECT DW FROM AccountDailyWithdraw DW WHERE DW.account.accountId = :accountId", AccountDailyWithdraw.class);
            query.setParameter("accountId", accountId);
            AccountDailyWithdraw accountDailyWithdraw= query.getSingleResult();
            return accountDailyWithdraw;
        } catch (RuntimeException error){
            if(error instanceof NoResultException){
                return null;
            }
            throw error;
        }
    }

}
