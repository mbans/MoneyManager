package com.lumar.moneymanager.repo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.lumar.moneymanager.domain.Account;
import com.mongodb.Mongo;

public class AccountRepoImpl implements AccountRepo {

	private Logger LOG = LoggerFactory.getLogger(AccountRepoImpl.class);

	// TODO: Pass in as properties;
	private static String databaseName = "money-manager";
	private String dbHost = "";
	private String dbPort = "";

	private Morphia morphia;
	private Mongo mongo;
	private Datastore ds;

	public AccountRepoImpl() {
		this(databaseName);
	}

	public AccountRepoImpl(String databaseName) {
		try {
			this.databaseName = databaseName;
			this.morphia = new Morphia();
			this.mongo = new Mongo();
			morphia.mapPackage("com.lumar.moneymanager.domain");
			ds = morphia.createDatastore(databaseName);
			LOG.info("Created Morphia DataSource for MongoDb database["
					+ databaseName + "]");
		} catch (Exception e) {
			throw new RuntimeException(
					"Exception instantiating Morphia instance for ["
							+ databaseName + "]");
		}
	}

	public Key<Account> saveAccount(Account account) {
		return ds.save(account);
	}

	public Set<Account> getAccountsByUsername(String username) {
		Query<Account> query = ds.createQuery(Account.class);
		query.criteria("accountOwnner").equal(username);
		Set<Account> accounts = new HashSet<Account>(query.asList());
		LOG.info("Retrieved ["+accounts.size()+"] accounts for "+username);
		return accounts;
	}
	
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}