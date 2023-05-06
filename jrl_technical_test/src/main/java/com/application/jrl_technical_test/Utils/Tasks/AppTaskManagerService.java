package com.application.jrl_technical_test.Utils.Tasks;

import com.application.jrl_technical_test.DAO.AppProgrammedTaskHome;
import com.application.jrl_technical_test.Entities.AppProgrammedTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@SpringBootApplication
@Stateless
public class AppTaskManagerService {

    private static final int MAX_ATTEMPTS = 3;
    
    private static final int TIME_ATTEMPS_DELETE = 100;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Autowired
    private AppTaskFactory appTaskFactory;
    
    @Autowired
    private AppProgrammedTaskHome appProgrammedTaskHome;
    
    private Map<String, Map<String, Object>> appTaskMap = new HashMap<>();
    
    @PostConstruct
    public void loadTasks(){
        List<AppProgrammedTask> appProgrammedTaskList = appProgrammedTaskHome.findAll();
        for(AppProgrammedTask task : appProgrammedTaskList){
            AppTaskType appTaskType = AppTaskType.findType(task.getTaskType());
            AppTask appTask = appTaskFactory.getInstanceAppTask(appTaskType);
            appTask.setKey(task.getCode());
            appTask.setDate(task.getExecutionDate());
            startTask(appTask);
        }
    }
    
    private void startTask(AppTask appTask){
        Timer timer = new Timer();
        Map<String, Object> taskMap = new HashMap<>();
        taskMap.put("timer", timer);
        taskMap.put("appTask", appTask);
        this.appTaskMap.put(appTask.getKey(), taskMap);
        timer.schedule(appTask, appTask.getDate());
    }
    
    @Transactional
    public void addTask(String key, Date date, AppTaskType appTaskType){
        try{
          AppProgrammedTask appProgrammedTask = new AppProgrammedTask();
          appProgrammedTask.setAppProgrammedTaskId(UUID.randomUUID().toString());
          appProgrammedTask.setCode(key);
          Calendar calendar = Calendar.getInstance();
          calendar.setTime(date);
          appProgrammedTask.setExecutionDate(calendar.getTime());
          appProgrammedTask.setTaskType(appTaskType.getType());
          appProgrammedTaskHome.persist(appProgrammedTask);
          
          AppTask appTask = appTaskFactory.getInstanceAppTask(appTaskType);
          appTask.setKey(key);
          appTask.setDate(date);
          
          startTask(appTask);
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public synchronized void clearTask(String key){
        try{
            int attempts = MAX_ATTEMPTS;
            while(attempts > 0){
                AppProgrammedTask appProgrammedTask = appProgrammedTaskHome.findByCode(key);
                if(appProgrammedTask != null){
                    appProgrammedTaskHome.remove(appProgrammedTask);
                    Timer timer = (Timer) this.appTaskMap.get(key).get("timer");
                    AppTask appTask = (AppTask) this.appTaskMap.get(key).get("appTask");
                    appTask.cancel();
                    timer.purge();
                    timer.cancel();
                    break;
                } else {
                    try{
                        this.wait(TIME_ATTEMPS_DELETE);
                    } catch (InterruptedException error){
                        Thread.currentThread().interrupt();
                        throw new RuntimeException();
                    }
                }
                attempts--;
            }
        } catch (Exception e){
            throw e;
        }
        
    }

}
