package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Client;
import com.application.jrl_technical_test.Entities.Movement;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SpringBootApplication
@Stateless
public class MovementHome {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Movement movement){
        try{
            entityManager.persist(movement);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public void remove(Movement movement){
        try{
            entityManager.remove(movement);
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Movement merge(Movement movement){
        try{
            Movement result = entityManager.merge(movement);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Movement findById(String movementId){
        try{
            Movement result = entityManager.find(Movement.class, movementId);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public List<Movement> findAllMovementByAccountID(String accountId){
        try{
            TypedQuery<Movement> query = entityManager.createQuery("SELECT M FROM Movement M WHERE M.account.accountId = :accountId", Movement.class);
            query.setParameter("accountId", accountId);
            List<Movement> movementList = query.getResultList();
            return movementList;
        } catch (RuntimeException error){
            throw error;
        }
    }

}
