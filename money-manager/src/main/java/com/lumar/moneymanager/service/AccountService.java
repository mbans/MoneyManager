package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.TransactionHeading;

public interface AccountService {

	public Key<Account> createAccount(String user, 
			 String accountName, 
			 String bank, 
			 String accountNum, 
			 String sortCode,
			 String sampleTransaction,
			 List<String> headings);
	
	public Key<Account> saveAccount(Account account);
	
	public Key<Account> updateAccount(Account account);

	/**
	 * Retrieves the accounts for the given user
	 * @param username
	 * @return
	 */
	public Set<Account> getAccountsForUser(String owner);
	
	/**
	 * Retrieves the account by owner and account name
	 * @param owner
	 * @param accountName
	 * @return
	 */
	public Account getAccountByOwnerAndName(String owner, String accountName);
	
	/**
	 * Deletes the given account
	 * @param account
	 */
	public void delete(Account account);
}
