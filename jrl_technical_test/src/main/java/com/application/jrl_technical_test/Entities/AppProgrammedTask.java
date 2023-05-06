package com.application.jrl_technical_test.Entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "APP_PROGRAMMED_TASK", uniqueConstraints = @UniqueConstraint(columnNames = "CODE"))
public class AppProgrammedTask {

    private String appProgrammedTaskId;
    private String code;
    private Date executionDate;
    private String taskType;

    public AppProgrammedTask(){}

    public AppProgrammedTask(String appProgrammedTaskId) {this.appProgrammedTaskId = appProgrammedTaskId;}

    public AppProgrammedTask(String appProgrammedTaskId, String code, Date executionDate, String taskType) {
        this.appProgrammedTaskId = appProgrammedTaskId;
        this.code = code;
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

    @Column(name = "CODE", unique = true, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
