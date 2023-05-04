package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Movement;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
