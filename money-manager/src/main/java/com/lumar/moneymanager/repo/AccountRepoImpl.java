package com.lumar.moneymanager.repo;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class AccountRepoImpl extends AbstractRepo implements AccountRepo  {

	private Logger LOG = LoggerFactory.getLogger(AccountRepoImpl.class);
	
	public AccountRepoImpl() {
		super();
	}
	
	public AccountRepoImpl(String databaseName) {
		super(databaseName);
	}
	
	public Key<Account> saveAccount(Account account) {
		return getDs().save(account);
	}

	public Set<Account> getAccountsByUsername(String username) {
		LOG.info("Retrieving Accounts for [{}]", username);
		Query<Account> query = getDs().createQuery(Account.class);
		query.criteria("accountOwner").equal(username);
		Set<Account> accounts = new HashSet<Account>(query.asList());
		LOG.info("Retrieved ["+accounts.size()+"] accounts for "+username);
		return accounts;
	}
	
	@Override
	public Account getAccountByAccountName(String accountName) {
		Query<Account> query = getDs().createQuery(Account.class);
		query.criteria("name").equal(accountName);
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