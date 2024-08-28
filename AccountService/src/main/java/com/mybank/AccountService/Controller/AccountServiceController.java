package com.mybank.AccountService.Controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybank.AccountService.Model.Account;
import com.mybank.AccountService.Service.AccountServiceService;
import com.mybank.AccountService.dto.AccountDto;
import com.mybank.AccountService.dto.StatementDTO;
import com.mybank.AccountService.dto.TransactionDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/account")
@Validated
public class AccountServiceController {
    
    @Autowired
    private AccountServiceService accountServiceService;

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceController.class);
    
    @PostMapping("/addAccount")
    @Operation(summary = "Add a new account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account added successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid account data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountDto> addAccounts(@Valid @RequestBody AccountDto account) {
        return ResponseEntity.ok(accountServiceService.addAccount(account));
    }
    
    @PutMapping("/updateAccount")
    @Operation(summary = "Update an existing account")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account updated successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid account data"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Account> updateAccount(@RequestBody AccountDto account) {
        return ResponseEntity.ok(accountServiceService.updateAccount(account));
    }

    @GetMapping("/{accountId}/balance")
    @Operation(summary = "Get account balance")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account balance retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountDto> getAccountBalance(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountServiceService.getAccount(accountId));
    }

    @GetMapping("/{accountId}/getAccountDetails")
    @Operation(summary = "Get account details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Account details retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AccountDto> getAccountDetails(@PathVariable Integer accountId) {
        return ResponseEntity.ok(accountServiceService.getAccountDetails(accountId));
    }

    @GetMapping("/{accountId}/transactionswithDate")
    @Operation(summary = "Get transactions between dates")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Bad request, invalid date format"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<StatementDTO> getTransactionsWithDate(
            @PathVariable Long accountId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.replace("Bearer ", "") : null;
        return ResponseEntity.ok(accountServiceService.getTransactionsBetweenDates(accountId, startDate, endDate, token)); 
    }

    @GetMapping("/{accountId}/transactions")
    @Operation(
        summary = "Get Transactions",
        description = "Retrieve transactions for a specific account.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Transactions retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @PathVariable Long accountId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        String token = (authHeader != null && authHeader.startsWith("Bearer ")) ? authHeader.replace("Bearer ", "") : null;

        List<TransactionDTO> transactions = accountServiceService.getTransactions(accountId, token);
        return ResponseEntity.ok(transactions);
    }

    @DeleteMapping("/delete/{accountId}")
    @Operation(
        summary = "Delete Account",
        description = "Delete an account by ID.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Account deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteAccount(
            @PathVariable Long accountId,
            @RequestHeader(value = "Authorization" ,required=false) String authHeader) throws Exception {
        
        String token = authHeader.replace("Bearer ", "");
        boolean deleted = accountServiceService.deleteAccount(accountId, token);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
