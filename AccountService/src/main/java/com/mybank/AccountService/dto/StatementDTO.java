package com.mybank.AccountService.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "accounId", "satrtDate", "endDate", "Statements" })
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatementDTO {
	private Long accounId;
	private List<TransactionDTO> Statements;
	private LocalDate satrtDate;
	private LocalDate endDate;
	public Long getAccounId() {
		return accounId;
	}
	public void setAccounId(Long accounId) {
		this.accounId = accounId;
	}
	public List<TransactionDTO> getStatements() {
		return Statements;
	}
	public void setStatements(List<TransactionDTO> statements) {
		Statements = statements;
	}
	public LocalDate getSatrtDate() {
		return satrtDate;
	}
	public void setSatrtDate(LocalDate satrtDate) {
		this.satrtDate = satrtDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "StatementDTO [accounId=" + accounId + ", Statements=" + Statements + ", satrtDate=" + satrtDate
				+ ", endDate=" + endDate + "]";
	}
	
	
}
