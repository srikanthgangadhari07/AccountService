package com.mybank.AccountService.dto;

import java.util.Date;

public class ErrorResponseDto {
	 private String status; 
	 private int statusCode;
	 private String message;
     private String  description;
     private Date timestamp;
     private String path;

	public ErrorResponseDto(String status, int statusCode, String message, String description, Date timestamp,
			String path) {
		super();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.description = description;
		this.timestamp = timestamp;
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "ErrorResponseDto [status=" + status + ", statusCode=" + statusCode + ", message=" + message
				+ ", description=" + description + ", timestamp=" + timestamp + ", path=" + path + "]";
	}

}
