package com.application.jrl_technical_test.Services.IServices;

import com.application.jrl_technical_test.Web.DTO.ServiceResponseDTO;

import java.util.Date;

public interface IReportService {

    ServiceResponseDTO getAccountStateReportByClient(String password, String clientId, String minDateString, String maxDateString) throws Exception;

}
