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
			 Set<String> cardNumbers,
			 Set<TransactionHeading> headings);
	
	public Key<Account> saveAccount(Account account);
	
	/**
	 * Retrieves the accounts for the given user
	 * @param username
	 * @return
	 */
	public Set<Account> getAccounts(String username);
	
}
