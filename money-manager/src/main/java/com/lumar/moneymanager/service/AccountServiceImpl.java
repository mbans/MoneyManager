package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.repo.AccountRepo;
import com.lumar.moneymanager.repo.AccountRepoImpl;

public class AccountServiceImpl implements AccountService {
	
	private static Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	public AccountRepo accountRepo; 

	public AccountServiceImpl() {
		this(null);
	}

	public AccountServiceImpl(String databaseName) {
		if(databaseName == null) {
			accountRepo = new AccountRepoImpl();
		}
		else {
			accountRepo = new AccountRepoImpl(databaseName);
		}
	}
	
	public Account getAccountByAccountName(String accountName) {
		return accountRepo.getAccountByAccountName(accountName);
	}
	
	public Key<Account> saveAccount(Account account) {
		Key<Account> savedAccount = accountRepo.saveAccount(account);
		LOG.info("Saved Account [{}]",savedAccount);
		return savedAccount;
	}
	
	public Key<Account> createAccount(String user, 
								 String accountName, 
								 String bank, 
								 String accountNum, 
								 String sortCode,
								 String sampleTransaction,
								 List<String> headings) {
		
		//Validation - is the account already registered, validate Input
		
		//Create the account
		Account account = new Account();
		account.setAccountOwner(user);
		account.setName(accountName);
		account.setBank(bank);
		account.setAccountNum(accountNum);
		account.setSortCode(sortCode);
		account.setTransactionHeadingOrdering(headings);
		return accountRepo.saveAccount(account);
	}
	
	public Set<Account> getAccounts(String username) {
		return accountRepo.getAccountsByUsername(username);
	}
	
}
