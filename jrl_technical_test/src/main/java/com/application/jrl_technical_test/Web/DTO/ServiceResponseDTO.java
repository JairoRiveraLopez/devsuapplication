package com.application.jrl_technical_test.Web.DTO;

import java.util.Map;

public class ServiceResponseDTO {

    private int statusCode;

    private Map<String, Object> information;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Object> getInformation() {
        return information;
    }

    public void setInformation(Map<String, Object> information) {
        this.information = information;
    }
}
