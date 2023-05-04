package com.application.jrl_technical_test.Web.DTO;

import com.application.jrl_technical_test.Utils.CodesConstants;

import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerDTO {

    private int statusCode;

    private Map<String, Object> body;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(int statusCode, Map<String, Object> data, boolean isData) {
        String status, description;
        Map<String, Object> body = new LinkedHashMap<>();
        switch (statusCode){
            case CodesConstants.SUCCESS_STATUS_CODE:
                status = "SUCCESS";
                description = "Request Correctly Executed";
                break;
            case CodesConstants.BAD_REQUEST_STATUS_CODE:
                status = "ERROR";
                description = "Request Not Executed";
                break;
            default:
                status = "UNCONTROLLED";
                description = "Failed by unknown reason";
        }
        body.put("status", status);
        body.put("description", description);
        body.put("data", (isData) ? (data) : (data.get("data")));
        this.body = body;

    }
}
