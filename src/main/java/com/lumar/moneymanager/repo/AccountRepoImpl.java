package com.lumar.moneymanager.repo;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class AccountRepoImpl extends AbstractRepo implements AccountRepo  {

	private Logger LOG = LoggerFactory.getLogger(AccountRepoImpl.class);
	
	public AccountRepoImpl(String databaseName) {
		super(databaseName);
	}
	
	public Key<Account> saveAccount(Account account) {
		return getDs().save(account);
	}

	public Key<Account> updateAccount(Account account) {

		//Delete existing
		delete(account);
		
		//Save new account
		return saveAccount(account);
	}

	@Override
	public void delete(Account account) {
		Account acc= getAccountByOwnerAndName(account.getAccountOwner(), account.getName());
		if(acc != null) {
			getDs().delete(acc);
		}			
		LOG.info("Account ["+acc+"] does not exist");
	}

	@Override
	public Set<Account> getAccountsForOwner(String username) {
		LOG.info("Retrieving Accounts for [{}]", username);
		Query<Account> query = getDs().createQuery(Account.class);
		query.criteria("accountOwner").equal(username);
		Set<Account> accounts = new HashSet<Account>(query.asList());
		LOG.info("Retrieved ["+accounts.size()+"] accounts for "+username);
		return accounts;
	}
	
	@Override
	public Account getAccountByOwnerAndName(String user, String accountName) {
		Query<Account> query = getDs().find(Account.class);
		query.and(query.criteria("name").equal(accountName), 
				  query.criteria("accountOwner").equal(user));

		Set<Account> accounts = new HashSet<Account>(query.asList());
		if(accounts.size() == 0) {
			LOG.info("No account with Account Name [{}]",accountName);
			return null;
		}
		else if(accounts.size() == 1) {
			return accounts.iterator().next();
		}
		else {
			throw new RuntimeException("There are multiple accounts with same name ["+accountName+"] dont know which one to retrieve");
		}
	}
}