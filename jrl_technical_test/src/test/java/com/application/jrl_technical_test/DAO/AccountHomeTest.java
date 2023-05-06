package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Account;
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
public class AccountHomeTest {

    @InjectMocks
    private AccountHome accountHome;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Account> accountTypedQuery;

    private Account account = new Account();

    private List<Account> accountList;

    private String accountId = "ACC999";

    @Before
    public void init(){
        accountList = new ArrayList<>();
        accountList.add(account);
    }

    @Test
    public void whenPersist_ThenOk() {
        account.setAccountId(accountId);
        accountHome.persist(account);
        ArgumentCaptor<Account> accountCapture = ArgumentCaptor.forClass(Account.class);
        verify(entityManager).persist(accountCapture.capture());
        assertEquals(account.getAccountId(), accountCapture.getValue().getAccountId());
    }

    @Test(expected = RuntimeException.class)
    public void whenPersist_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).persist(account);
        accountHome.persist(account);
    }

    @Test
    public void whenRemove_ThenOk() {
        account.setAccountId(accountId);
        accountHome.remove(account);
        ArgumentCaptor<Account> accountCapture = ArgumentCaptor.forClass(Account.class);
        verify(entityManager).remove(accountCapture.capture());
        assertEquals(account.getAccountId(), accountCapture.getValue().getAccountId());
    }

    @Test(expected = RuntimeException.class)
    public void whenRemove_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).remove(account);
        accountHome.remove(account);
    }

    @Test
    public void whenMerge_ThenOk() {
        account.setAccountId(accountId);
        when(entityManager.merge(account)).thenReturn(account);
        Account response = accountHome.merge(account);
        assertEquals(accountId, response.getAccountId());
    }

    @Test(expected = RuntimeException.class)
    public void whenMerge_ThenException() {
        account.setAccountId(accountId);
        when(entityManager.merge(account)).thenThrow(new RuntimeException());
        accountHome.merge(account);
    }

    @Test
    public void whenFindById_ThenOk() {
        account.setAccountId(accountId);
        when(entityManager.find(Account.class,accountId)).thenReturn(account);
        Account response = accountHome.findById(accountId);
        assertEquals(accountId, response.getAccountId());
    }

    @Test(expected = RuntimeException.class)
    public void whenFindById_ThenException() {
        account.setAccountId(accountId);
        when(entityManager.find(Account.class,accountId)).thenThrow(new RuntimeException());
        accountHome.findById(accountId);
    }

    @Test
    public void whenFindByAccountNumber_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(Account.class))).thenReturn(accountTypedQuery);
        when(accountTypedQuery.getSingleResult()).thenReturn(account);
        Account response = accountHome.findByAccountNumber(toString());
        assertEquals(response, account);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindByAccountNumber_ThenException() {
        when(entityManager.createQuery(anyString(), eq(Account.class))).thenThrow(new RuntimeException());
        accountHome.findByAccountNumber(toString());
    }
}
