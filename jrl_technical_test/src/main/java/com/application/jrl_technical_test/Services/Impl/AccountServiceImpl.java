package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.AccountHome;
import com.application.jrl_technical_test.DAO.ClientHome;
import com.application.jrl_technical_test.DAO.MovementHome;
import com.application.jrl_technical_test.Entities.Account;
import com.application.jrl_technical_test.Entities.Client;
import com.application.jrl_technical_test.Entities.Movement;
import com.application.jrl_technical_test.Services.IServices.IAccountDayLimitService;
import com.application.jrl_technical_test.Services.IServices.IAccountService;
import com.application.jrl_technical_test.Services.ValidatorService;
import com.application.jrl_technical_test.Utils.CodesConstants;
import com.application.jrl_technical_test.Utils.ConstantsUtil;
import com.application.jrl_technical_test.Utils.FormatUtil;
import com.application.jrl_technical_test.Utils.MessagesUtil;
import com.application.jrl_technical_test.Web.DTO.AccountQueryDTO;
import com.application.jrl_technical_test.Web.DTO.AccountTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountHome accountHome;

    @Autowired
    private ClientHome clientHome;

    @Autowired private MovementHome movementHome;

    @Autowired
    private ValidatorService validatorService;

    @Autowired
    private IAccountDayLimitService accountDayLimitService;

    @Override
    @Transactional()
    public ServiceResponseDTO insertAccount(AccountTransactionDTO accountTransactionDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(accountTransactionDTO)){
            try{
                Boolean successfull = false;
                Object account = instantiateAccount(null, accountTransactionDTO, "PERSIST");
                if(account != null){
                    if(account instanceof Account){
                        successfull = true;
                        accountHome.persist((Account) account);
                    }
                }
                if(successfull){
                    accountDayLimitService.persistAccountDayLimit(((Account) account).getAccountId());
                    data.put("message", MessagesUtil.ACCOUNT_PERSIST_SUCCESS);
                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.ACCOUNT_PERSIST_WRONG_CLIENT);
                }

            }catch (Exception error){
                throw error;
            }
        } else {
            data.put("message", MessagesUtil.ACCOUNT_PERSIST_FAIL);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO updateAccount(String accountId, AccountTransactionDTO accountTransactionDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(accountTransactionDTO)){
            try{
                Optional<Account> accountQuery = Optional.ofNullable(accountHome.findById(accountId));
                if(accountQuery.isPresent()){
                    Object account = instantiateAccount(accountQuery.get(), accountTransactionDTO, "UPDATE");
                    if(account instanceof Account){
                        accountHome.merge((Account) account);
                    }
                    data.put("message", MessagesUtil.ACCOUNT_UPDATE_SUCCESS);
                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.ACCOUNT_UPDATE_FAIL_NOT_FOUND);
                }
            }catch (Exception error){
                throw error;
            }
        } else {
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.ACCOUNT_UPDATE_FAIL_MISSING_DATA);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO editAccount(String accountId, String dataType, String value) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(accountId.isEmpty() || dataType.isEmpty() || value.isEmpty()){
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.ACCOUNT_EDIT_FAIL);
        }else {
            try{
                Optional<Account> accountQuery = Optional.ofNullable(accountHome.findById(accountId));
                if(accountQuery.isPresent()){
                    Account account = accountQuery.get();
                    if(editAccountInformation(dataType, value, account)){
                        data.put("message", MessagesUtil.ACCOUNT_EDIT_SUCCESS);
                    }else {
                        statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                        data.put("message", MessagesUtil.ACCOUNT_EDIT_FAIL_WRONG_DATATYPE);
                    }

                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.ACCOUNT_EDIT_FAIL_NOT_FOUND);
                }
            }catch (Exception error){
                throw error;
            }
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO removeAccount(String accountId) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            Optional<Account> accountQuery = Optional.ofNullable(accountHome.findById(accountId));
            if(accountQuery.isPresent()) {
                if(removeMovementsWhenRemoveAccount(accountQuery.get())){
                    accountHome.remove(accountQuery.get());
                    data.put("message", MessagesUtil.ACCOUNT_REMOVE_SUCCESS);
                }else {
                    statusCode = CodesConstants.INTERNAL_SERVER_ERROR;
                    data.put("message", MessagesUtil.ACCOUNT_REMOVE_FAIL_REMOVING_MOVEMENTS);
                }
            }else {
                statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                data.put("message", MessagesUtil.ACCOUNT_REMOVE_FAIL);
            }
            ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
            serviceResponseDTO.setStatusCode(statusCode);
            serviceResponseDTO.setInformation(data);
            return serviceResponseDTO;
        }catch (Exception error){
            throw error;
        }
    }

    @Override
    public ServiceResponseDTO findAccountByAccountNumber(String accountNumber) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            Optional<Account> accountQuery = Optional.ofNullable(accountHome.findByAccountNumber(accountNumber));
            if(accountQuery.isPresent()) {
                Object account = instantiateAccount(accountQuery.get(), null, "findByAccount");
                if(account instanceof AccountQueryDTO){
                    data.put("accountFound",account);
                }
            }else {
                statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                data.put("message", MessagesUtil.ACCOUNT_FIND_IDENTIFICATION_NOT_FOUND);
            }
            ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
            serviceResponseDTO.setStatusCode(statusCode);
            serviceResponseDTO.setInformation(data);
            return serviceResponseDTO;
        }catch (Exception e){
            throw e;
        }
    }

    private Object instantiateAccount(Account accountQuery, AccountTransactionDTO accountTransactionDTO, String action){
        if(action.equals("PERSIST")) {
            Account account = new Account();
            account.setAccountId(UUID.randomUUID().toString());
            account.setAccountNumber(accountTransactionDTO.getAccountNumber());
            account.setAccountType(accountTransactionDTO.getAccountType());
            account.setInitialBalance(accountTransactionDTO.getInitialBalance());
            account.setState(accountTransactionDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            Optional<Client> client = Optional.ofNullable(clientHome.findById(accountTransactionDTO.getClientId()));
            if(client.isPresent()){
                account.setClient(client.get());
            } else {
                return null;
            }
            return account;
        }else if(action.equals("UPDATE")){
            Account account = new Account();
            account.setAccountId(accountQuery.getAccountId());
            account.setAccountNumber(accountTransactionDTO.getAccountNumber());
            account.setAccountType(accountTransactionDTO.getAccountType());
            account.setInitialBalance(accountTransactionDTO.getInitialBalance());
            account.setState(accountTransactionDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            Optional<Client> client = Optional.ofNullable(clientHome.findById(accountTransactionDTO.getClientId()));
            if(client.isPresent()){
                account.setClient(client.get());
            } else {
                return null;
            }
            return account;
        }else {
            AccountQueryDTO accountQueryDTO = new AccountQueryDTO();
            accountQueryDTO.setAccountId(accountQuery.getAccountId());
            accountQueryDTO.setAccountNumber(accountQuery.getAccountNumber());
            accountQueryDTO.setAccountType(accountQuery.getAccountType());
            accountQueryDTO.setInitialBalance(accountQuery.getInitialBalance());
            accountQueryDTO.setState(accountQuery.getState().equals(ConstantsUtil.ACTIVE.getValue()) ? "ACTIVE" : "INACTIVE");
            Client client = clientHome.findById(accountQuery.getClient().getClientId());
            accountQueryDTO.setClientName(FormatUtil.fullName(client.getName(), client.getLastName1(), client.getLastName2(), FormatUtil.upperCase));
            return accountQueryDTO;
        }
    }

    private Boolean editAccountInformation(String dataType, String value, Account account){
        Boolean somethingChanged = true;
        try {
            switch (dataType) {
                case "accountNumber":
                    account.setAccountNumber(value);
                    accountHome.merge(account);
                    break;
                case "accountType":
                    account.setAccountType(value);
                    accountHome.merge(account);
                    break;
                case "initialBalance":
                    account.setInitialBalance(BigDecimal.valueOf(Long.parseLong(value)));
                    accountHome.merge(account);
                    break;
                case "state":
                    account.setState(value.equals("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()  : ConstantsUtil.INACTIVE.getValue());
                    accountHome.merge(account);
                    break;
                default:
                    somethingChanged = false;
            }
            return somethingChanged;
        } catch (Exception error){
            throw error;
        }
    }

    private Boolean removeMovementsWhenRemoveAccount(Account account){
        try{
            List<Movement> movementList = movementHome.findAllMovementByAccountID(account.getAccountId());
            Boolean listRemoved = false;
            if(!movementList.isEmpty()){
                for(Movement mov : movementList){
                    listRemoved = true;
                    movementHome.remove(mov);
                }
            }
            return listRemoved;
        }catch (Exception e){
            throw e;
        }
    }

}
