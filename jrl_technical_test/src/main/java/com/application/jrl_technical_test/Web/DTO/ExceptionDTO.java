package com.application.jrl_technical_test.Web.DTO;

import java.time.LocalDate;

public class ExceptionDTO {

    private String error;
    private  String message;
    private int status;
    private LocalDate date;

    public static ExceptionDTO getExceptionDTO(String error, String message, int status){
        ExceptionDTO exceptionDTO = new ExceptionDTO();
        exceptionDTO.setError(error);
        exceptionDTO.setMessage(message);
        exceptionDTO.setStatus(status);
        exceptionDTO.setDate(LocalDate.now());
        return exceptionDTO;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
