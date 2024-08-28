package com.mybank.AccountService.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.mybank.AccountService.Exception.BadRequestException;
import com.mybank.AccountService.Model.Account;
import com.mybank.AccountService.Repository.AccountServiceRepository;
import com.mybank.AccountService.dto.AccountDto;
import com.mybank.AccountService.dto.StatementDTO;
import com.mybank.AccountService.dto.TransactionDTO;

@Service
public class AccountServiceService {
	@Autowired
	AccountServiceRepository accountRepository;

	@Autowired
	RestTemplate restTemplate;
	public static final Logger LOGGER =LoggerFactory.getLogger(AccountServiceService.class);

	public AccountDto addAccount(AccountDto accountdto){
		LOGGER.info("In  AccountServiceService.addAccount()");
		try {	
			Account account = new Account(); 
			validateAccount(accountdto);
			BeanUtils.copyProperties(accountdto, account);
			account.setCreatedAt(new Date());
			Account accountSaved = accountRepository.save(account);
			BeanUtils.copyProperties(accountSaved, accountdto);
			LOGGER.info("Out AccountServiceService.addAccount()");
		}catch(Exception e){
			LOGGER.info("Exception occured while adding account");
			throw e;
		}
		return  accountdto;
	}
	public boolean deleteAccount(Long accountId,String jwtToken) throws Exception{
		LOGGER.info("In  AccountServiceService.deleteAccount()");
		boolean status=false;
		status = deleteTransactions(accountId,jwtToken);
		LOGGER.info("delete transactions status : "+status);
		LOGGER.info("Out AccountServiceService.deleteAccount()");
		return(accountRepository.deleteByAccountId(accountId) )> 0;
	}
	public AccountDto getAccount(Integer accountId){
		AccountDto accountDto =new AccountDto();
		LOGGER.info("In  AccountServiceService.getAccount()");
		Account accountDetails  = accountRepository.findByAccountId(accountId);
		if(accountDetails!=null) {
			accountDto.setAccountId(accountDetails.getAccountId());
			accountDto.setAmount(accountDetails.getAmount());
		}
		LOGGER.info("Out AccountServiceService.getAccount()");
		return  accountDto;
	}
	public AccountDto getAccountDetails(Integer accountId){
		AccountDto accountDto =new AccountDto();
		LOGGER.info("In  AccountServiceService.getAccount()");
		Account accountDetails  = accountRepository.findByAccountId(accountId);
		if(accountDetails !=null) {
			BeanUtils.copyProperties(accountDetails, accountDto);
		}
		LOGGER.info("Out AccountServiceService.getAccount()");
		return  accountDto;
	}
	public Account updateAccount(AccountDto accountDto){
		LOGGER.info("In  AccountServiceService.addAccount()");
		Account existingaccountDetails  = accountRepository.findByAccountId(accountDto.getAccountId());
		if(existingaccountDetails == null) {
			throw new BadRequestException("invalid account id ", "account id :"+accountDto.getAccountId() +" account doesn't exist");

		}
		if(!canUpdateAccountType(existingaccountDetails.getAccountType(),accountDto.getAccountType())) {
			throw new BadRequestException("invalid account type", "Can not update "+existingaccountDetails.getAccountType()+" to "+accountDto.getAccountType()+", as its not valid.");

		}
		if(accountDto.getAccountType()!=null) {
			existingaccountDetails.setAccountType(accountDto.getAccountType());
		}

		if( (accountDto.getEmail() !=null) 
				&& (!accountDto.getEmail().isEmpty())
				&& (validate(accountDto.getEmail(),"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$"))) {
			existingaccountDetails.setEmail(accountDto.getEmail());
		}
		if(accountDto.getUserName()!=null && !accountDto.getUserName().isEmpty()) {
			existingaccountDetails.setUserName(accountDto.getUserName());
		}
		if(accountDto.getAmount()!=null ) {
			existingaccountDetails.setAmount(accountDto.getAmount());
		}
		if((accountDto.getAdharNumber()!=null) && (!accountDto.getAdharNumber().isEmpty())  &&  (validate(accountDto.getAdharNumber(),"^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$"))) {
			existingaccountDetails.setAdharNumber(accountDto.getAdharNumber());
		}
		LOGGER.info("Out AccountServiceService.addAccount()"+accountDto);
		Account accountSaved = accountRepository.save(existingaccountDetails);
		LOGGER.info("Out AccountServiceService.addAccount()");
		return  accountSaved;
	}
	public StatementDTO getTransactionsBetweenDates(Long accountId,LocalDate startDate , LocalDate endDate,String jwtToken) {
		LOGGER.info("In AccountServiceService.getTransactionsBetweenDates()");
		List<TransactionDTO> transactionsList = new ArrayList<>();
		StatementDTO statementDTO =new StatementDTO();
		try {
			HttpHeaders httpHeaders= new HttpHeaders();
			httpHeaders.set("Authorization", "Bearer " + jwtToken);
			HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
			String url= "http://gatewayservice/transactionservice/transaction/"+accountId +"/transactions?startDate="+startDate+ "&endDate="+endDate;
			//			 String url="http://transactionservice/transactionService/9/transactions?startDate=2024-08-05&endDate=2024-08-05";
			LOGGER.info("URL: "+url);
			ResponseEntity<List<TransactionDTO>>  resp  =null;
			resp = restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<List<TransactionDTO>>() {
			});
			transactionsList =resp.getBody();
			statementDTO.setAccounId(accountId);
			statementDTO.setStatements(transactionsList);
			statementDTO.setSatrtDate(startDate);
			statementDTO.setEndDate(endDate);	 

		}catch(HttpClientErrorException e) {
			LOGGER.info("Exception occured while fetching transactions", e.getMessage());
			throw e;	
		}
		catch(Exception e) {
			LOGGER.info("Exception occured while fetching transactions", e.getMessage());
			throw e;	
		}
		LOGGER.info("Out AccountServiceService.getTransactionsBetweenDates()");
		return statementDTO;
	}
	public  List<TransactionDTO> getTransactions(Long accountId,String jwtToken) {
		LOGGER.info("In AccountServiceService.getTransactions()");
		List<TransactionDTO> transactionsList = new ArrayList<>();
		try {
			HttpHeaders httpHeaders= new HttpHeaders();
			httpHeaders.set("Authorization", "Bearer " + jwtToken);
			HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
			String url= "http://gatewayservice/transactionservice/transaction/"+accountId +"/getTransactions";
//			String url= "http://gateway-server/transactionservice/transaction/"+accountId +"/getTransactions";
			LOGGER.info("URL: "+url);
			ResponseEntity<List<TransactionDTO>>  resp  =null;
			resp = restTemplate.exchange(url, HttpMethod.GET, entity,new ParameterizedTypeReference<List<TransactionDTO>>() {
			});
			transactionsList =resp.getBody();
		}catch(HttpClientErrorException e) {
			LOGGER.info("Exception occured while fetching transactions", e.getMessage());
			throw e;	
		}
		catch(Exception e) {
			LOGGER.info("Exception occured while fetching transactions", e.getMessage());
			throw e;	
		}
		LOGGER.info("Out AccountServiceService.getTransactions()");
		return transactionsList;
	}
	
	public boolean deleteTransactions(Long accountId,String  jwtToken) {
		LOGGER.info("In AccountServiceService.deleteTransactions()");
		boolean status =false;
		try {
			HttpHeaders httpHeaders=new HttpHeaders() ;
			httpHeaders.set("Authorization", "Bearer " + jwtToken);
			HttpEntity<?> entity = new HttpEntity<>(httpHeaders);
			String url= "http://gatewayservice/transactionservice/transaction/"+accountId;
			LOGGER.info("URL: "+url);
			ResponseEntity<Void>  resp  =null;
			resp = restTemplate.exchange(url, HttpMethod.DELETE, entity,Void.class);	
			if(resp.getStatusCode()  == HttpStatus.NO_CONTENT) {
				status= true;
			}

		}catch(HttpClientErrorException e) {
			LOGGER.info("Exception occured while deleting transactions", e.getMessage());
			throw e;	
		}
		catch(Exception e) {
			LOGGER.info("Exception occured while fetching transactions", e.getMessage());
			throw e;	
		}
		LOGGER.info("Out AccountServiceService.deleteTransactions()");
		return status;
	}
	public void validateAccount(AccountDto accountdto ) {
		LOGGER.info("In  AccountServiceService.validateAccount()");
		if(accountRepository.existsByAdharNumber(accountdto.getAdharNumber())) {
			throw new BadRequestException("adhar number is already exist", "adhar number :"+accountdto.getAdharNumber() +" already exist ,it must be unique");

		}
		if(!Arrays.asList("SAVINGS","CURRENT","FIXED_DEPOSIT").contains(accountdto.getAccountType().toUpperCase())) {
			throw new BadRequestException("acount type is invalid", "account type :"+accountdto.getAccountType() +" is not valid, please enter valid type");

		}

		if(accountdto.getAmount()<1000) {
			throw new BadRequestException("initial deposit amount should be greater than 1000", "deposit amount "+accountdto.getAccountType() +"is less than the minimum required amount");

		}
		LOGGER.info("Out AccountServiceService.validateAccount()");
	}
	public boolean canUpdateAccountType(String existingaccountType, String newAccountType){
		return((newAccountType !=null) && (!newAccountType.isEmpty()) 
				&& ((Arrays.asList("SAVINGS","CURRENT","FIXED_DEPOSIT").contains(newAccountType.toUpperCase())) 
						&& ( !("CURRENT".equalsIgnoreCase(existingaccountType) 
								&& "FIXED_DEPOSIT".equalsIgnoreCase(newAccountType))))); 
	}
	public boolean validate(String input ,String pattern ) {
		LOGGER.info("In  AccountServiceService.validate()");
		if(!input.matches(pattern)) {
			throw new BadRequestException("input is invalid", "input :"+input +" is not valid, please enter valid  input");
		}
		return true;

	}
}
