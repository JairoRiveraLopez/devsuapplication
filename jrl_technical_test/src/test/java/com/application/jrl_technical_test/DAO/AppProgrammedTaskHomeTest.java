package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.AppProgrammedTask;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AppProgrammedTaskHomeTest {

    @InjectMocks
    private AppProgrammedTaskHome appProgrammedTaskHome;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<AppProgrammedTask> appProgrammedTaskQuery;

    private AppProgrammedTask appProgrammedTask = new AppProgrammedTask();

    private List<AppProgrammedTask> appProgrammedTaskList;

    private String appProgrammedTaskId = "APT999";

    @Before
    public void init(){
        appProgrammedTaskList = new ArrayList<>();
        appProgrammedTaskList.add(appProgrammedTask);
    }

    @Test
    public void whenPersist_ThenOk() {
        appProgrammedTask.setAppProgrammedTaskId(appProgrammedTaskId);
        appProgrammedTaskHome.persist(appProgrammedTask);
        ArgumentCaptor<AppProgrammedTask> appProgrammedTaskArgumentCaptor = ArgumentCaptor.forClass(AppProgrammedTask.class);
        verify(entityManager).persist(appProgrammedTaskArgumentCaptor.capture());
        assertEquals(appProgrammedTask.getAppProgrammedTaskId(), appProgrammedTaskArgumentCaptor.getValue().getAppProgrammedTaskId());
    }

    @Test(expected = RuntimeException.class)
    public void whenPersist_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).persist(appProgrammedTask);
        appProgrammedTaskHome.persist(appProgrammedTask);
    }

    @Test
    public void whenRemove_ThenOk() {
        appProgrammedTask.setAppProgrammedTaskId(appProgrammedTaskId);
        appProgrammedTaskHome.remove(appProgrammedTask);
        ArgumentCaptor<AppProgrammedTask> appProgrammedTaskArgumentCaptor = ArgumentCaptor.forClass(AppProgrammedTask.class);
        verify(entityManager).remove(appProgrammedTaskArgumentCaptor.capture());
        assertEquals(appProgrammedTask.getAppProgrammedTaskId(), appProgrammedTaskArgumentCaptor.getValue().getAppProgrammedTaskId());
    }

    @Test(expected = RuntimeException.class)
    public void whenRemove_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).remove(appProgrammedTask);
        appProgrammedTaskHome.remove(appProgrammedTask);
    }

    @Test
    public void whenMerge_ThenOk() {
        appProgrammedTask.setAppProgrammedTaskId(appProgrammedTaskId);
        when(entityManager.merge(appProgrammedTask)).thenReturn(appProgrammedTask);
        AppProgrammedTask response = appProgrammedTaskHome.merge(appProgrammedTask);
        assertEquals(appProgrammedTaskId, response.getAppProgrammedTaskId());
    }

    @Test(expected = RuntimeException.class)
    public void whenMerge_ThenException() {
        appProgrammedTask.setAppProgrammedTaskId(appProgrammedTaskId);
        when(entityManager.merge(appProgrammedTask)).thenThrow(new RuntimeException());
        appProgrammedTaskHome.merge(appProgrammedTask);
    }

    @Test
    public void whenByCode_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(AppProgrammedTask.class))).thenReturn(appProgrammedTaskQuery);
        when(appProgrammedTaskQuery.getSingleResult()).thenReturn(appProgrammedTask);
        AppProgrammedTask response = appProgrammedTaskHome.findByCode(toString());
        assertEquals(response, appProgrammedTask);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindByAccountNumber_ThenException() {
        when(entityManager.createQuery(anyString(), eq(AppProgrammedTask.class))).thenThrow(new RuntimeException());
        appProgrammedTaskHome.findByCode(toString());
    }

    @Test
    public void whenFindAll_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(AppProgrammedTask.class))).thenReturn(appProgrammedTaskQuery);
        when(appProgrammedTaskQuery.getResultList()).thenReturn(appProgrammedTaskList);
        List<AppProgrammedTask> response = appProgrammedTaskHome.findAll();
        assertEquals(response.get(0), appProgrammedTask);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindAll_ThenException() {
        when(entityManager.createQuery(anyString(), eq(AppProgrammedTask.class))).thenThrow(new RuntimeException());
        appProgrammedTaskHome.findAll();
    }

}
