package com.application.jrl_technical_test.DAO;

import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.Client;
import com.application.jrl_technical_test.Entities.Movement;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ClientHomeTest {

    @InjectMocks
    private ClientHome clientHome;

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery<Client> clientTypedQuery;

    @Mock
    private TypedQuery<Object[]> stateAccountTypedQuery;

    private Client client = new Client();

    private String clientId = "C999";

    @Test
    public void whenPersist_ThenOk() {
        client.setClientId(clientId);
        clientHome.persist(client);
        ArgumentCaptor<Client> clientCapture = ArgumentCaptor.forClass(Client.class);
        verify(entityManager).persist(clientCapture.capture());
        assertEquals(client.getClientId(), clientCapture.getValue().getClientId());
    }

    @Test(expected = RuntimeException.class)
    public void whenPersist_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).persist(client);
        clientHome.persist(client);
    }

    @Test
    public void whenRemove_ThenOk() {
        client.setClientId(clientId);
        clientHome.remove(client);
        ArgumentCaptor<Client> clientCapture = ArgumentCaptor.forClass(Client.class);
        verify(entityManager).remove(clientCapture.capture());
        assertEquals(client.getClientId(), clientCapture.getValue().getClientId());
    }

    @Test(expected = RuntimeException.class)
    public void whenRemove_ThenException() {
        doThrow(new RuntimeException()).when(entityManager).remove(client);
        clientHome.remove(client);
    }

    @Test
    public void whenMerge_ThenOk() {
        client.setClientId(clientId);
        when(entityManager.merge(client)).thenReturn(client);
        Client response = clientHome.merge(client);
        assertEquals(clientId, response.getClientId());
    }

    @Test(expected = RuntimeException.class)
    public void whenMerge_ThenException() {
        client.setClientId(clientId);
        when(entityManager.merge(client)).thenThrow(new RuntimeException());
        clientHome.merge(client);
    }

    @Test
    public void whenFindById_ThenOk() {
        client.setClientId(clientId);
        when(entityManager.find(Client.class,clientId)).thenReturn(client);
        Client response = clientHome.findById(clientId);
        assertEquals(clientId, response.getClientId());
    }

    @Test(expected = RuntimeException.class)
    public void whenFindById_ThenException() {
        client.setClientId(clientId);
        when(entityManager.find(Client.class,clientId)).thenThrow(new RuntimeException());
        clientHome.findById(clientId);
    }

    @Test
    public void whenFindByIdentification_ThenOk() {
        when(entityManager.createQuery(anyString(), eq(Client.class))).thenReturn(clientTypedQuery);
        when(clientTypedQuery.getSingleResult()).thenReturn(client);
        Client response = clientHome.findByIdentification(toString());
        assertEquals(response, client);
    }

    @Test(expected = RuntimeException.class)
    public void whenFindByIdentification_ThenException() {
        when(entityManager.createQuery(anyString(), eq(Client.class))).thenThrow(new RuntimeException());
        clientHome.findByIdentification(toString());
    }

    @Test
    public void findAccountStateReportByClient_thenOk(){
        Client clientQuery = new Client();
        clientQuery.setClientId(clientId);
        Account account = new Account();
        account.setAccountId("AC999");
        Movement movement = new Movement();
        movement.setIdMovement("MV999");
        movement.setMovementDate(new Date());

        Object[] reportInfo = new Object[]{ movement, account, clientQuery };
        List<Object[]> reportInfoList = new ArrayList<>();
        reportInfoList.add(reportInfo);

        when(entityManager.createQuery(anyString(), eq(Object[].class))).thenReturn(stateAccountTypedQuery);
        when(stateAccountTypedQuery.getResultList()).thenReturn(reportInfoList);
        List<Object[]> report = clientHome.findAccountStateReportByClient(clientId, new Date(), new Date());
        assertEquals(clientQuery.getClientId(), ((Client) report.get(0)[2]).getClientId());
        assertEquals(account.getAccountId(), ((Account) report.get(0)[1]).getAccountId());
        assertEquals(movement.getIdMovement(), ((Movement) report.get(0)[0]).getIdMovement());
    }
}
