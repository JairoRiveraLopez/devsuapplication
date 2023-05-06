package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.AccountDailyWithdraw;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountDailyWithdrawHomeTest {

    @InjectMocks
    private AccountDailyWithdrawHome accountDailyWithdrawHome;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<AccountDailyWithdraw> accountDailyWithdrawTypedQuery;

    private AccountDailyWithdraw accountDailyWithdraw = new AccountDailyWithdraw();
    private List<AccountDailyWithdraw> accountDailyWithdrawList;

    private String accountDailyWithdrawId = "ADW999";

    @Before
    public void init(){
        accountDailyWithdrawList = new ArrayList<>();
        accountDailyWithdrawList.add(accountDailyWithdraw);
    }

    @Test
    public void whenPersist_ThenOk() {
        accountDailyWithdraw.setAccountDailyWithdrawId(accountDailyWithdrawId);
        accountDailyWithdrawHome.persist(accountDailyWithdraw);
        ArgumentCaptor<AccountDailyWithdraw> accountDailyWithdrawCapture = ArgumentCaptor.forClass(AccountDailyWithdraw.class);
        verify(entityManager).persist(accountDailyWithdrawCapture.capture());
        assertEquals(accountDailyWithdraw.getAccountDailyWithdrawId(), accountDailyWithdrawCapture.getValue().getAccountDailyWithdrawId());
    }

    @Test(expected = RuntimeException.class)
    public void whenPersist_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).persist(accountDailyWithdraw);
        accountDailyWithdrawHome.persist(accountDailyWithdraw);
    }

    @Test
    public void whenRemove_ThenOk() {
        accountDailyWithdraw.setAccountDailyWithdrawId(accountDailyWithdrawId);
        accountDailyWithdrawHome.remove(accountDailyWithdraw);
        ArgumentCaptor<AccountDailyWithdraw> accountDailyWithdrawCapture = ArgumentCaptor.forClass(AccountDailyWithdraw.class);
        verify(entityManager).remove(accountDailyWithdrawCapture.capture());
        assertEquals(accountDailyWithdraw.getAccountDailyWithdrawId(), accountDailyWithdrawCapture.getValue().getAccountDailyWithdrawId());
    }

    @Test(expected = RuntimeException.class)
    public void whenRemove_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).remove(accountDailyWithdraw);
        accountDailyWithdrawHome.remove(accountDailyWithdraw);
    }

    @Test
    public void whenMerge_ThenOk() {
        accountDailyWithdraw.setAccountDailyWithdrawId(accountDailyWithdrawId);
        when(entityManager.merge(accountDailyWithdraw)).thenReturn(accountDailyWithdraw);
        AccountDailyWithdraw response = accountDailyWithdrawHome.merge(accountDailyWithdraw);
        assertEquals(accountDailyWithdrawId, response.getAccountDailyWithdrawId());
    }

    @Test(expected = RuntimeException.class)
    public void whenMerge_ThenException() {
        accountDailyWithdraw.setAccountDailyWithdrawId(accountDailyWithdrawId);
        when(entityManager.merge(accountDailyWithdraw)).thenThrow(new RuntimeException());
        accountDailyWithdrawHome.merge(accountDailyWithdraw);
    }

    @Test
    public void whenFindAll_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(AccountDailyWithdraw.class))).thenReturn(accountDailyWithdrawTypedQuery);
        when(accountDailyWithdrawTypedQuery.getResultList()).thenReturn(accountDailyWithdrawList);
        List<AccountDailyWithdraw> response = accountDailyWithdrawHome.findAll();
        assertEquals(response.get(0), accountDailyWithdraw);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindAll_ThenException() {
        when(entityManager.createQuery(anyString(), eq(AccountDailyWithdraw.class))).thenThrow(new RuntimeException());
        accountDailyWithdrawHome.findAll();
    }

    @Test
    public void whenFindByAccountId_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(AccountDailyWithdraw.class))).thenReturn(accountDailyWithdrawTypedQuery);
        when(accountDailyWithdrawTypedQuery.getSingleResult()).thenReturn(accountDailyWithdraw);
        AccountDailyWithdraw response = accountDailyWithdrawHome.findByAccountId(toString());
        assertEquals(response, accountDailyWithdraw);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindByAccountId_ThenException() {
        when(entityManager.createQuery(anyString(), eq(AccountDailyWithdraw.class))).thenThrow(new RuntimeException());
        accountDailyWithdrawHome.findByAccountId(toString());
    }
}
