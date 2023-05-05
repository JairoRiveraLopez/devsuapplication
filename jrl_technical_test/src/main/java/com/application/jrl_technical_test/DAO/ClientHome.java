package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Client;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public Client findByIdentification(String identification){
        try{
            TypedQuery<Client> query = entityManager.createQuery("SELECT C FROM Client C WHERE C.identification = :identification", Client.class);
            query.setParameter("identification", identification);
            Client client = (Client) query.getSingleResult();
            return client;
        } catch (RuntimeException error){
            if(error instanceof NoResultException){
                return null;
            }
            throw error;
        }
    }

}
