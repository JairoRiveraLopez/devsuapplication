package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Client;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
@Stateless
public class ClientHome {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(Client client){
        try{
            entityManager.persist(client);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public void remove(Client client){
        try{
            entityManager.remove(client);
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Client merge(Client client){
        try{
            Client result = entityManager.merge(client);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public Client findById(String clientId){
        try{
            Client result = entityManager.find(Client.class, clientId);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

}
