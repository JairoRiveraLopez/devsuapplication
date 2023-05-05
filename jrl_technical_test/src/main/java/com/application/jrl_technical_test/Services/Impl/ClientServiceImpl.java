package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.ClientHome;
import com.application.jrl_technical_test.Web.DTO.ClientQueryDTO;
import com.application.jrl_technical_test.Web.DTO.ClientTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import com.application.jrl_technical_test.Entities.Client;
import com.application.jrl_technical_test.Security.ClientPasswordEncoder;
import com.application.jrl_technical_test.Services.IServices.IClientService;
import com.application.jrl_technical_test.Utils.CodesConstants;
import com.application.jrl_technical_test.Utils.ConstantsUtil;
import com.application.jrl_technical_test.Utils.MessagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientHome clientHome;

    @Autowired
    private ValidatorService validatorService;

    private static final ClientPasswordEncoder clientPasswordEncoder = new ClientPasswordEncoder();

    @Override
    @Transactional()
    public ServiceResponseDTO insertClient(ClientTransactionDTO clientTransactionDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(clientTransactionDTO)){
            try{
                Object client = instantiateClient(null, clientTransactionDTO, "PERSIST");
                if(client instanceof Client){
                    clientHome.persist((Client) client);
                }
                data.put("message", MessagesUtil.CLIENT_PERSIST_SUCCESS);
            }catch (Exception error){
                throw error;
            }
        } else {
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.CLIENT_PERSIST_FAIL);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO updateClient(String clientId, ClientTransactionDTO clientTransactionDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(clientTransactionDTO)){
            try{
                Optional<Client> clientQuery = Optional.ofNullable(clientHome.findById(clientId));
                if(clientQuery.isPresent()){
                    Object client = instantiateClient(clientQuery.get(), clientTransactionDTO, "UPDATE");
                    if(client instanceof Client){
                        clientHome.merge((Client) client);
                    }
                    data.put("message", MessagesUtil.CLIENT_UPDATE_SUCCESS);
                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.CLIENT_UPDATE_FAIL_NOT_FOUND);
                }
            }catch (Exception error){
                throw error;
            }
        } else {
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.CLIENT_UPDATE_FAIL_MISSING_DATA);
        }
        ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
        serviceResponseDTO.setStatusCode(statusCode);
        serviceResponseDTO.setInformation(data);
        return serviceResponseDTO;
    }

    @Override
    @Transactional()
    public ServiceResponseDTO editClient(String clientId, String dataType, String value) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(clientId.isEmpty() || dataType.isEmpty() || value.isEmpty()){
            statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
            data.put("message", MessagesUtil.CLIENT_EDIT_FAIL);
        }else {
            try{
                Optional<Client> clientQuery = Optional.ofNullable(clientHome.findById(clientId));
                if(clientQuery.isPresent()){
                    Client client = clientQuery.get();
                    if(editClientInformation(dataType, value, client)){
                        data.put("message", MessagesUtil.CLIENT_EDIT_SUCCESS);
                    }else {
                        statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                        data.put("message", MessagesUtil.CLIENT_EDIT_FAIL_WRONG_DATATYPE);
                    }

                } else {
                    statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                    data.put("message", MessagesUtil.CLIENT_EDIT_FAIL_NOT_FOUND);
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
    public ServiceResponseDTO removeClient(String clientId) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            Optional<Client> clientQuery = Optional.ofNullable(clientHome.findById(clientId));
            if(clientQuery.isPresent()) {
                clientHome.remove(clientQuery.get());
                data.put("message", MessagesUtil.CLIENT_REMOVE_SUCCESS);
            }else {
                statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                data.put("message", MessagesUtil.CLIENT_REMOVE_FAIL);
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
    public ServiceResponseDTO findClientByIdentification(String identification) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        try{
            Optional<Client> clientQuery = Optional.ofNullable(clientHome.findByIdentification(identification));
            if(clientQuery.isPresent()) {
                Object client = instantiateClient(clientQuery.get(), null, "findById");
                if(client instanceof ClientQueryDTO){
                    data.put("clientFound",client);
                }
            }else {
                statusCode = CodesConstants.BAD_REQUEST_STATUS_CODE;
                data.put("message", MessagesUtil.CLIENT_FIND_BY_ID_NOT_FOUND);
            }
            ServiceResponseDTO serviceResponseDTO = new ServiceResponseDTO();
            serviceResponseDTO.setStatusCode(statusCode);
            serviceResponseDTO.setInformation(data);
            return serviceResponseDTO;
        }catch (Exception e){
            throw e;
        }
    }

    private Object instantiateClient(Client clientQuery, ClientTransactionDTO clientTransactionDTO, String action){
        Client client = new Client();
        ClientQueryDTO clientQueryDTOResponse = new ClientQueryDTO();
        if(action.equals("PERSIST")){
            client.setClientId(UUID.randomUUID().toString());
            client.setPassword(clientPasswordEncoder.encode(clientTransactionDTO.getPassword()));
            client.setState(clientTransactionDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            client.setPersonId(UUID.randomUUID().toString());
            client.setIdentification(clientTransactionDTO.getIdentification());
            client.setName(clientTransactionDTO.getName());
            client.setLastName1(clientTransactionDTO.getLastName1());
            client.setLastName2(clientTransactionDTO.getLastName2());
            client.setGenre(clientTransactionDTO.getGenre().equalsIgnoreCase("MALE") ? ConstantsUtil.MALE.getValue()
                    : ConstantsUtil.FEMALE.getValue());
            client.setAge(clientTransactionDTO.getAge());
            client.setAddress(clientTransactionDTO.getAddress());
            client.setPhone(clientTransactionDTO.getPhone());
        } else if(action.equals("UPDATE")){
            client.setClientId(clientQuery.getClientId());
            client.setPassword(clientPasswordEncoder.encode(clientTransactionDTO.getPassword()));
            client.setState(clientTransactionDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            client.setPersonId(clientQuery.getPersonId());
            client.setIdentification(clientTransactionDTO.getIdentification());
            client.setName(clientTransactionDTO.getName());
            client.setLastName1(clientTransactionDTO.getLastName1());
            client.setLastName2(clientTransactionDTO.getLastName2());
            client.setGenre(clientTransactionDTO.getGenre().equalsIgnoreCase("MALE") ? ConstantsUtil.MALE.getValue()
                    : ConstantsUtil.FEMALE.getValue());
            client.setAge(clientTransactionDTO.getAge());
            client.setAddress(clientTransactionDTO.getAddress());
            client.setPhone(clientTransactionDTO.getPhone());
        }else{
            clientQueryDTOResponse.setClientId(clientQuery.getClientId());
            clientQueryDTOResponse.setState(clientQuery.getState().equals(ConstantsUtil.ACTIVE.getValue()) ? "ACTIVE" : "INACTIVE");
            clientQueryDTOResponse.setIdentification(clientQuery.getIdentification());
            clientQueryDTOResponse.setName(clientQuery.getName());
            clientQueryDTOResponse.setLastName1(clientQuery.getLastName1());
            clientQueryDTOResponse.setLastName2(clientQuery.getLastName2());
            clientQueryDTOResponse.setGenre(clientQuery.getGenre().equals(ConstantsUtil.MALE.getValue()) ? "MALE" : "FEMALE");
            clientQueryDTOResponse.setAge(clientQuery.getAge());
            clientQueryDTOResponse.setAddress(clientQuery.getAddress());
            clientQueryDTOResponse.setPhone(clientQuery.getPhone());
            return clientQueryDTOResponse;
        }
        return client;
    }

    private Boolean editClientInformation(String dataType, String value, Client client){
        Boolean somethingChanged = true;
        try {
            switch (dataType) {
                case "password":
                    client.setPassword(clientPasswordEncoder.encode(value));
                    clientHome.merge(client);
                    break;
                case "state":
                    client.setState(value.equals("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()  : ConstantsUtil.INACTIVE.getValue());
                    clientHome.merge(client);
                    break;
                case "name":
                    client.setName(value);
                    clientHome.merge(client);
                    break;
                case "lastName1":
                    client.setLastName1(value);
                    clientHome.merge(client);
                    break;
                case "lastName2":
                    client.setLastName2(value);
                    clientHome.merge(client);
                    break;
                case "age":
                    client.setAge(Integer.valueOf(value));
                    clientHome.merge(client);
                    break;
                case "genre":
                    client.setGenre(value.equals("MALE") ? ConstantsUtil.MALE.getValue() : ConstantsUtil.FEMALE.getValue());
                    clientHome.merge(client);
                    break;
                case "address":
                    client.setAddress(value);
                    clientHome.merge(client);
                    break;
                case "phone":
                    client.setPhone(value);
                    clientHome.merge(client);
                    break;
                default:
                    somethingChanged = false;
            }
            return somethingChanged;
        } catch (Exception error){
            throw error;
        }
    }
}
