package com.borneo.framework.common.exception;


/**
 * @author jackyyong
 * Copyright 2014 Nexstream SDN. BHD.
 * Date: Jul 24, 2014 6:52:45 PM
 * Tags: 
 */
public class CustomGenericException extends RuntimeException{

	private static final long serialVersionUID = 7104305913776672257L;

	private String errCode;
	private String errMsg;
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public CustomGenericException(String errCode,String errMsg){
		this.errCode=errCode;
		this.errMsg=errMsg;
	}
	
}
