package com.borneo.framework.json.mo;

/**
 * @author jackyyong
 * Copyright 2015 Nexstream Sdn. Bhd.
 */
public class ErrorResp {
	private String code;
	private String message;
	
	public String getClazz() {
		return this.getClass().getSimpleName();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
