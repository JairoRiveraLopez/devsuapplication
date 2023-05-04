package com.application.jrl_technical_test.Services.Impl;

import com.application.jrl_technical_test.DAO.ClientHome;
import com.application.jrl_technical_test.Web.DTO.ClientDTO;
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
    public ServiceResponseDTO insertClient(ClientDTO clientDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(clientDTO)){
            try{
                Client client = instantiateClient(null, clientDTO, "PERSIST");
                clientHome.persist(client);
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
    public ServiceResponseDTO updateClient(String clientId, ClientDTO clientDTO) throws Exception {
        int statusCode = ((int) CodesConstants.SUCCESS_STATUS_CODE);
        Map<String, Object> data = new LinkedHashMap<>();
        if(!validatorService.objectHasNullAttributes(clientDTO) && !clientId.isEmpty()){
            try{
                Optional<Client> clientQuery = Optional.ofNullable(clientHome.findById(clientId));
                if(clientQuery.isPresent()){
                    Client client = instantiateClient(clientQuery.get(), clientDTO, "UPDATE");
                    clientHome.merge(client);
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
                    editClientInformation(dataType, value, client);
                    data.put("message", MessagesUtil.CLIENT_EDIT_SUCCESS);
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

    private Client instantiateClient(Client clientQuery, ClientDTO clientDTO, String action){
        Client client = new Client();
        if(action.equals("PERSIST")){
            client.setClientId(UUID.randomUUID().toString());
            client.setPassword(clientPasswordEncoder.encode(clientDTO.getPassword()));
            client.setState(clientDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            client.setPersonId(UUID.randomUUID().toString());
            client.setIdentification(clientDTO.getIdentification());
            client.setName(clientDTO.getName());
            client.setLastName1(clientDTO.getLastName1());
            client.setLastName2(clientDTO.getLastName2());
            client.setGenre(clientDTO.getGenre().equalsIgnoreCase("MALE") ? ConstantsUtil.MALE.getValue()
                    : ConstantsUtil.FEMALE.getValue());
            client.setAge(clientDTO.getAge());
            client.setAddress(clientDTO.getAddress());
            client.setPhone(clientDTO.getPhone());
        } else if(action.equals("UPDATE")){
            client.setClientId(clientQuery.getClientId());
            client.setPassword(clientPasswordEncoder.encode(clientDTO.getPassword()));
            client.setState(clientDTO.getState().equalsIgnoreCase("ACTIVE") ? ConstantsUtil.ACTIVE.getValue()
                    : ConstantsUtil.INACTIVE.getValue());
            client.setPersonId(clientQuery.getPersonId());
            client.setIdentification(clientDTO.getIdentification());
            client.setName(clientDTO.getName());
            client.setLastName1(clientDTO.getLastName1());
            client.setLastName2(clientDTO.getLastName2());
            client.setGenre(clientDTO.getGenre().equalsIgnoreCase("MALE") ? ConstantsUtil.MALE.getValue()
                    : ConstantsUtil.FEMALE.getValue());
            client.setAge(clientDTO.getAge());
            client.setAddress(clientDTO.getAddress());
            client.setPhone(clientDTO.getPhone());
        }

        return client;
    }



    private Character getStateByStringValue(String value){
        Character response;
        switch (value) {
            case "ACTIVE":
                response = ConstantsUtil.ACTIVE.getValue();
                break;
            case "INACTIVE":
                response = ConstantsUtil.INACTIVE.getValue();
                break;
            default:
                response = null;
        }
        return response;
    }

    private void editClientInformation(String dataType, String value, Client client){
        try {
            switch (dataType) {
                case "password":
                    client.setPassword(clientPasswordEncoder.encode(value));
                    clientHome.merge(client);
                    break;
                case "state":
                    if (getStateByStringValue(value) == null) {
                        break;
                    } else {
                        client.setState(getStateByStringValue(value));
                        clientHome.merge(client);
                    }
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
                    client.setGenre(value.equals("M") ? ConstantsUtil.MALE.getValue() : ConstantsUtil.FEMALE.getValue());
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
            }
        } catch (Exception error){
            throw error;
        }
    }
}
