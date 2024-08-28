package com.mybank.AccountService.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;



public class TransactionDTO {
	private Long transactionId;
	
	private Long accountId;
	
	private String transactionType;
	
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	private Double amount;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime timeStamp;
	
	private  Long fromAccountId;
	
	private Long toAccountId;

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Long getFromAccountId() {
		return fromAccountId;
	}

	public void setFromAccountId(Long fromAccountId) {
		this.fromAccountId = fromAccountId;
	}

	public Long getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(Long toAccountId) {
		this.toAccountId = toAccountId;
	}

	@Override
	public String toString() {
		return "TransactionDto [transactionId=" + transactionId + ", accountId=" + accountId + ", transactionType="
				+ transactionType + ", amount=" + amount + ", timeStamp=" + timeStamp + ", fromAccountId="
				+ fromAccountId + ", toAccountId=" + toAccountId + "]";
	}
}
	