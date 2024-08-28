package com.mybank.AccountService.Exception;

@SuppressWarnings("serial")
public class BadRequestException  extends RuntimeException{
	private String message;
	private String description;
	public BadRequestException(String message, String description) {
		super();
		this.message = message;
		this.description = description;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


}
