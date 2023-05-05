package com.application.jrl_technical_test.Web.Controller;

import com.application.jrl_technical_test.Services.IServices.IClientService;
import com.application.jrl_technical_test.Web.DTO.ClientTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ControllerDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @GetMapping(value = "/find-client-by-identification")
    public ResponseEntity<ControllerDTO> editClient(@RequestHeader String identification) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.findClientByIdentification(identification);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PostMapping(value = "/persist-client")
    public ResponseEntity<ControllerDTO> persistClient(@RequestBody ClientTransactionDTO clientTransactionDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.insertClient(clientTransactionDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PutMapping(value = "/update-client")
    public ResponseEntity<ControllerDTO> updateClient(@RequestHeader String clientId, @RequestBody ClientTransactionDTO clientTransactionDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.updateClient(clientId, clientTransactionDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PatchMapping(value = "/edit-client")
    public ResponseEntity<ControllerDTO> editClient(@RequestParam String idClient, @RequestParam String dataType, @RequestParam String value) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.editClient(idClient, dataType, value);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @DeleteMapping(value = "/remove-client")
    public ResponseEntity<ControllerDTO> removeClient(@RequestHeader String clientId) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.removeClient(clientId);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

}
