package com.application.jrl_technical_test.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APP_PROGRAMMED_TASK")
public class AppProgrammedTask {

    private String appProgrammedTaskId;
    private String key;
    private Date executionDate;
    private String taskType;

    public AppProgrammedTask(){}

    public AppProgrammedTask(String appProgrammedTaskId, String key, Date executionDate, String taskType) {
        this.appProgrammedTaskId = appProgrammedTaskId;
        this.key = key;
        this.executionDate = executionDate;
        this.taskType = taskType;
    }

    @Id
    @Column(name = "ID_PROGRAMMED_TASK", unique = true, length = 36)
    public String getAppProgrammedTaskId() {
        return appProgrammedTaskId;
    }

    public void setAppProgrammedTaskId(String appProgrammedTaskId) {
        this.appProgrammedTaskId = appProgrammedTaskId;
    }

    @Column(name = "KEY", length = 50)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    @Temporal(TemporalType.DATE)
    @Column(name = "EXECUTION_DATE", length = 19)
    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    @Column(name = "TASK_TYPE", length = 36)
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }
}
