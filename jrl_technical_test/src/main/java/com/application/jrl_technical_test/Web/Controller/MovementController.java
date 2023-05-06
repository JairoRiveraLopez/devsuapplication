package com.application.jrl_technical_test.Web.Controller;

import com.application.jrl_technical_test.Services.IServices.IMovementService;
import com.application.jrl_technical_test.Web.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RequestMapping("/movements")
public class MovementController {

    @Autowired
    private IMovementService movementService;

    @PostMapping(value = "/persist-movement")
    public ResponseEntity<ControllerDTO> persistMovement(@RequestBody MovementPersistDTO movementPersistDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = movementService.insertMovement(movementPersistDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PutMapping(value = "/update-movement")
    public ResponseEntity<ControllerDTO> updateMovement(@RequestHeader String movementId, @RequestBody MovementUpdateDTO movementUpdateDTO) throws Exception {
        ServiceResponseDTO serviceResponseDTO = movementService.updateMovement(movementId, movementUpdateDTO);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @PatchMapping(value = "/edit-movement")
    public ResponseEntity<ControllerDTO> editMovement(@RequestParam String movementId, @RequestParam String dataType, @RequestParam String value) throws Exception {
        ServiceResponseDTO serviceResponseDTO = movementService.editMovement(movementId, dataType, value);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

    @DeleteMapping(value = "/remove-movement")
    public ResponseEntity<ControllerDTO> removeMovement(@RequestHeader String movementId) throws Exception {
        ServiceResponseDTO serviceResponseDTO = movementService.removeMovement(movementId);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

}
