package com.application.jrl_technical_test.Exception;

import com.application.jrl_technical_test.Web.DTO.ExceptionDTO;

public class InternalAppException extends AppException{

    public InternalAppException() {
    }

    public InternalAppException(ExceptionDTO exceptionDTO) {
        super(exceptionDTO);
    }
}
