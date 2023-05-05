package com.application.jrl_technical_test.Web.Controller;

import com.application.jrl_technical_test.Services.IServices.IAccountService;
import com.application.jrl_technical_test.Web.DTO.AccountTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ClientTransactionDTO;
import com.application.jrl_technical_test.Web.DTO.ControllerDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @GetMapping(value = "/find-account-by-account-number")
    public ResponseEntity<ControllerDTO> findAccountByAccountNumber(@RequestHeader String accountNumber) throws Exception {
        ServiceResponseDTO serviceResponseDTO = accountService.findAccountByAccountNumber(accountNumber);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PostMapping(value = "/persist-account")
    public ResponseEntity<ControllerDTO> persistAccount(@RequestBody AccountTransactionDTO accountTransactionDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = accountService.insertAccount(accountTransactionDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PutMapping(value = "/update-account")
    public ResponseEntity<ControllerDTO> updateAccount(@RequestHeader String accountId, @RequestBody AccountTransactionDTO accountTransactionDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = accountService.updateAccount(accountId, accountTransactionDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PatchMapping(value = "/edit-account")
    public ResponseEntity<ControllerDTO> editAccount(@RequestParam String accountId, @RequestParam String dataType, @RequestParam String value) throws Exception {
        ServiceResponseDTO serviceResponseDTO = accountService.editAccount(accountId, dataType, value);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @DeleteMapping(value = "/remove-account")
    public ResponseEntity<ControllerDTO> removeAccount(@RequestHeader String accountId) throws Exception {
        ServiceResponseDTO serviceResponseDTO = accountService.removeAccount(accountId);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

}
