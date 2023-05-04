package com.application.jrl_technical_test.Web.Controller;

import com.application.jrl_technical_test.Services.IServices.IClientService;
import com.application.jrl_technical_test.Web.DTO.ClientDTO;
import com.application.jrl_technical_test.Web.DTO.ControllerDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @PostMapping(value = "/persist-client")
    public ResponseEntity<ControllerDTO> persistClient(@RequestBody ClientDTO clientDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.insertClient(clientDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PutMapping(value = "/update-client")
    public ResponseEntity<ControllerDTO> updateClient(@RequestBody ClientDTO clientDTO, @RequestHeader String idClient) throws Exception {
        ServiceResponseDTO serviceResponseDTO = clientService.updateClient(idClient, clientDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

}
