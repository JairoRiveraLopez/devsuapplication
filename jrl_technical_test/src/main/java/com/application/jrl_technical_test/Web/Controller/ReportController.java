package com.application.jrl_technical_test.Web.Controller;

import com.application.jrl_technical_test.Services.IServices.IReportService;
import com.application.jrl_technical_test.Web.DTO.ControllerDTO;
import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping(value = "/get-account-state-report")
    public ResponseEntity<ControllerDTO> getAccountStateReport(@RequestParam String password, @RequestParam String clientId,
                                                               @RequestParam String minDateString, @RequestParam String maxDateString) throws Exception {
        ServiceResponseDTO serviceResponseDTO = reportService.getAccountStateReportByClient(password, clientId, minDateString, maxDateString);
        ControllerDTO controllerDTO = new ControllerDTO();
        controllerDTO.setStatusCode(serviceResponseDTO.getStatusCode());
        controllerDTO.setBody(serviceResponseDTO.getStatusCode(), serviceResponseDTO.getInformation(), true);
        ResponseEntity<ControllerDTO> response = ResponseEntity.status(HttpStatus.OK).body(controllerDTO);
        return response;
    }

}
