package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Web.DTO.AccountTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;

public interface IAccountService {

    ServiceResponseDTO insertAccount(AccountTransactionDTO accountTransactionDTO) throws Exception;

    ServiceResponseDTO updateAccount(String accountId, AccountTransactionDTO accountTransactionDTO) throws Exception;

    ServiceResponseDTO editAccount(String accountId, String dataType, String value) throws Exception;

    ServiceResponseDTO removeAccount(String accountId) throws Exception;

    ServiceResponseDTO findAccountByAccountNumber(String accountNumber) throws Exception;

}
