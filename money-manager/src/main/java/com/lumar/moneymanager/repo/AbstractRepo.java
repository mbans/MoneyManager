package com.lumar.moneymanager.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class AbstractRepo {

	private Logger LOG = LoggerFactory.getLogger(AccountRepoImpl.class);
	
	// TODO: Pass in as properties;
	private static String databaseName = "money-manager";
	private String dbHost = "";
	private String dbPort = "";
	private String dbName;
	
	private Morphia morphia;
	private Datastore ds;
	private Mongo mongo;
	
	public AbstractRepo() {
		this(databaseName);
	}
	
	public AbstractRepo(String databaseName) {
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
	
	public Datastore getDs() {
		return ds;
	}
	
	public void setDbName(String databaseName) {
		this.dbName = databaseName;
	}
}
