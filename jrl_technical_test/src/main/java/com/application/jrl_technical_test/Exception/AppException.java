package com.application.jrl_technical_test.Exception;

import com.application.jrl_technical_test.Web.DTO.ExceptionDTO;

public abstract class AppException extends Exception{

    private static final long serialVersionUID = 1L;

    private ExceptionDTO exceptionDTO;

    public AppException(){super();}

    public AppException(ExceptionDTO exceptionDTO){
        super(exceptionDTO.getError());
        this.exceptionDTO = exceptionDTO;
    }

    public ExceptionDTO getExceptionDTO() {
        return exceptionDTO;
    }

    public void setExceptionDTO(ExceptionDTO exceptionDTO) {
        this.exceptionDTO = exceptionDTO;
    }
}
