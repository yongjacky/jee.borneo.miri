package com.borneo.framework.json.mo;

import com.borneo.framework.common.utils.EConstant;

public class RestResponse {

    private Object data;

    private String status = EConstant.JsonResult.SUCCESS.toString().toLowerCase();

    private String message = EConstant.JsonResult.SUCCESS.toString().toLowerCase();

    public RestResponse() {
    }

    public RestResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public RestResponse(Object data, String status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
