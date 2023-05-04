package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Web.DTO.ClientDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import com.application.jrl_technical_test.Entities.Client;
import org.springframework.validation.annotation.Validated;

public interface IClientService {

    ServiceResponseDTO insertClient(ClientDTO clientDTO) throws Exception;

    ServiceResponseDTO updateClient(String clientId, ClientDTO clientDTO) throws Exception;

    ServiceResponseDTO editClient(String clientId, String dataType, String value) throws Exception;

    ServiceResponseDTO removeClient(String clientId) throws Exception;

}
