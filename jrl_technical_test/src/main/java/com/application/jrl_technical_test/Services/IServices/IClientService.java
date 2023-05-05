package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Web.DTO.ClientTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;

public interface IClientService {

    ServiceResponseDTO insertClient(ClientTransactionDTO clientTransactionDTO) throws Exception;

    ServiceResponseDTO updateClient(String clientId, ClientTransactionDTO clientTransactionDTO) throws Exception;

    ServiceResponseDTO editClient(String clientId, String dataType, String value) throws Exception;

    ServiceResponseDTO removeClient(String clientId) throws Exception;

    ServiceResponseDTO findClientByIdentification(String identification) throws Exception;

}
