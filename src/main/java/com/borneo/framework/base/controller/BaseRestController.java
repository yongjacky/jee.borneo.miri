package com.borneo.framework.base.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.borneo.framework.json.mo.ErrorResp;

public abstract class BaseRestController extends BaseController{
	
	static final String ERROR_EXCEPTION = "-501";

	@RequestMapping(value="**")
	@ResponseBody
	protected Object handleResourceNotFound(){
		ErrorResp resp = new ErrorResp();
		resp.setCode("404");
		resp.setMessage("Resource Not Found!");
		return resp;
	}
	
	protected HttpServletResponse enableCrossOrigin(HttpServletResponse res) {
        res.addHeader("Access-Control-Allow-Origin", "*");
        return res;
    }
	
	public ErrorResp getErrorException(Exception ex){
		ErrorResp errorResp = new ErrorResp();
		errorResp.setCode(ERROR_EXCEPTION);
		errorResp.setMessage("Error: "+ex.getMessage());
		
		return errorResp;
	}
}
