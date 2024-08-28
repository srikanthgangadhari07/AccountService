package com.mybank.AccountService.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDto {
	
    private Integer accountId;
    @NotNull(message ="user name  is must not be null")
    @NotEmpty(message ="user name type is must not be empty")
	private String userName;
    
    @Email(message = "Invalid email format")
    @NotEmpty
	private String email;
    
    @NotNull(message ="account type is must not be null")
    @NotEmpty(message ="account type is must not be empty")
	private String accountType;
    
	private Double amount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
	
	 @NotNull
//	 @Schema(example = "1234-5678-9012-1234")
	 @Pattern(regexp="^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$",message = "adhar number must be 16 digits")
	private String adharNumber;
	 
	private String status;
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getAdharNumber() {
		return adharNumber;
	}
	public void setAdharNumber(String adharNumber) {
		this.adharNumber = adharNumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "AccountDto [accountId=" + accountId + ", userName=" + userName + ", email=" + email + ", accountType="
				+ accountType + ", amount=" + amount + ", createdAt=" + createdAt + ", adharNumber=" + adharNumber
				+ ", status=" + status + "]";
	}
	

	

}
