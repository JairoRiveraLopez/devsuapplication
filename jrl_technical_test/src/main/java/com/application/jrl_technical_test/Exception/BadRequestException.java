package com.application.jrl_technical_test.Exception;

import com.application.jrl_technical_test.Web.DTO.ExceptionDTO;

public class BadRequestException extends AppException{

    public BadRequestException() {
    }

    public BadRequestException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO);
    }
}
