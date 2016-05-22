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

	public AccountServiceImpl(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
		verifyConnection();
	}
	
	@Override
	public Account getAccountByOwnerAndName(String accountOwner, String accountName) {
		return accountRepo.getAccountByOwnerAndName(accountOwner, accountName);
	}
	
	@Override
	public Key<Account> saveAccount(Account account) {
		LOG.info("Checking if Account["+account.getName()+"] already exists");
		Account existingAccount = accountRepo.getAccountByOwnerAndName(account.getAccountOwner(), account.getName());

		if(existingAccount != null) {
			throw new RuntimeException("There already exists an account with accountName ["+ account.getName() + "]");
		}
		
		//Save account
		Key<Account> savedAccount = accountRepo.saveAccount(account);
		LOG.info("Saved Account [{}]",savedAccount);
		return savedAccount;
	}
	
	@Override
	public Key<Account> updateAccount(Account account) {
		return accountRepo.updateAccount(account);
	}
	
	@Override
	public void delete(Account account) {
		accountRepo.delete(account);
	}
	
	@Override
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
	
	@Override
	public Set<Account> getAccountsForUser(String owner) {
		return accountRepo.getAccountsForOwner(owner);
	}
	
	private void checkForTabDelimiter(Account account) {
		if("Tab".equals(account.getDelimiter())) {
			account.setDelimiter("\t");
		}
	}

	private void verifyConnection() {
		try {
			accountRepo.getAccountsForOwner("");
		}
		catch(Exception e) {
			throw new RuntimeException("Looks like the DB is down.....get it started (mongod)");
		}
	}
	
}
