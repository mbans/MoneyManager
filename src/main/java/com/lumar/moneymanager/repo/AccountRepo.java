package com.lumar.moneymanager.repo;

import java.util.Set;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Account;
 
public interface AccountRepo {
	

	public Key<Account> saveAccount(Account account);

	public Key<Account> updateAccount(Account account);

	/**
	 * Retrieves account for user and accountName
	 * @param user
	 * @param accountName
	 * @return
	 */
	public Account getAccountByOwnerAndName(String user,String accountName);

	/**
	 * Get accounts for accountOwner
	 * @param username
	 * @return
	 */
	public Set<Account> getAccountsForOwner(String username);
		
	public void delete(Account account);
	
}
