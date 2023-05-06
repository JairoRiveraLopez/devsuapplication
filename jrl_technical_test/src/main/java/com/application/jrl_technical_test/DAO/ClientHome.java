package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Client;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

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

    public List<Object[]> findAccountStateReportByClient(String clientId, Date minDate, Date maxDate){
        String SQL = "SELECT mv, acc, cl FROM Movement mv " +
                "LEFT JOIN mv.account acc " +
                "LEFT JOIN acc.client cl " +
                "WHERE (  " +
                "cl.clientId = :clientId AND " +
                "mv.movementDate between :minDate and :maxDate) ";
        try{
            TypedQuery<Object[]> query = entityManager.createQuery(SQL, Object[].class);
            query.setParameter("clientId", clientId);
            query.setParameter("minDate", minDate);
            query.setParameter("maxDate", maxDate);
            List<Object[]> accountStateList = query.getResultList();
            return accountStateList;
        }catch (RuntimeException error){
            throw error;
        }
    }

}
