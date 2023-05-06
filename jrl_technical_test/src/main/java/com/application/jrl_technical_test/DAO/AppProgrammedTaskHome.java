package com.application.jrl_technical_test.DAO;


import com.application.jrl_technical_test.Entities.AppProgrammedTask;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@SpringBootApplication
@Stateless
public class AppProgrammedTaskHome {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(AppProgrammedTask appProgrammedTask){
        try{
            entityManager.persist(appProgrammedTask);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public void remove(AppProgrammedTask appProgrammedTask){
        try{
            entityManager.remove(appProgrammedTask);
            entityManager.flush();
        } catch (RuntimeException error){
            throw error;
        }
    }

    public AppProgrammedTask merge(AppProgrammedTask appProgrammedTask){
        try{
            AppProgrammedTask result = entityManager.merge(appProgrammedTask);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public AppProgrammedTask findById(String appProgrammedTaskId){
        try{
            AppProgrammedTask result = entityManager.find(AppProgrammedTask.class, appProgrammedTaskId);
            return result;
        } catch (RuntimeException error){
            throw error;
        }
    }

    public AppProgrammedTask findByCode(String code){
        try{
            TypedQuery<AppProgrammedTask> query = entityManager.createQuery("SELECT PT FROM AppProgrammedTask PT WHERE PT.code = :code", AppProgrammedTask.class);
            query.setParameter("code", code);
            AppProgrammedTask appProgrammedTask = query.getSingleResult();
            return appProgrammedTask;
        } catch (RuntimeException error){
            if(error instanceof NoResultException){
                return null;
            }
            throw error;
        }
    }

    public List<AppProgrammedTask> findAll(){
        try{
            TypedQuery<AppProgrammedTask> query = entityManager.createQuery("SELECT PT FROM AppProgrammedTask PT", AppProgrammedTask.class);
            List<AppProgrammedTask> appProgrammedTaskList = query.getResultList();
            return appProgrammedTaskList;
        } catch (RuntimeException error){
            if(error instanceof NoResultException){
                return null;
            }
            throw error;
        }
    }

}
