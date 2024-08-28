package com.mybank.AccountService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mybank.AccountService.Model.Account;

import jakarta.transaction.Transactional;
@Transactional
public interface  AccountServiceRepository extends JpaRepository<Account,Integer> {
	@Query("SELECT a FROM Account a WHERE a.accountId = :accountId")
	Account findByAccountId(@Param("accountId") Integer accountId);
	
	@Modifying
	@Query("delete from Account a where a.accountId = :accountId")
	 int  deleteByAccountId(@Param("accountId") Long accountId);
	
	 boolean existsByAdharNumber(String adharNumber);

}
