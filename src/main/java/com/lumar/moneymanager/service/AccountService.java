package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Account;

public interface AccountService {

	public Key<Account> createAccount(String user, 
			 String accountName, 
			 String bank, 
			 String accountNum, 
			 String sortCode,
			 String dateFormat,
			 String sampleTransaction,
			 List<String> headings);
	
	Key<Account> saveAccount(Account account);
	
	Key<Account> updateAccount(Account account);

	Set<Account> getAccountsForUser(String owner);
	
	/**
	 * Retrieves the account by owner and account name
	 * @param owner
	 * @param accountName
	 * @return
	 */
	Account getAccountByOwnerAndName(String owner, String accountName);
	
	/**
	 * Deletes the given account
	 * @param account
	 */
	void delete(Account account);
}
