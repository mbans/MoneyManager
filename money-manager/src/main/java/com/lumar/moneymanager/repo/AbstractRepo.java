package com.lumar.moneymanager.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Key;
import com.google.code.morphia.Morphia;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.BigDecimalBigDecimalMorphiaConverter;
import com.lumar.moneymanager.service.BigDecimalConverter;
import com.mongodb.Mongo;

public class AbstractRepo {

	private Logger LOG = LoggerFactory.getLogger(AccountRepoImpl.class);
	
	// TODO: Pass in as properties;
	private String dbHost = "";
	private String dbPort = "";
	private String dbName;
	
	private Morphia morphia;
	private Datastore ds;
	private Mongo mongo;
	
	public AbstractRepo(String databaseName) {
		try {
			this.morphia = new Morphia();
			this.mongo = new Mongo();
			morphia.mapPackage("com.lumar.moneymanager.domain");
			morphia.getMapper().getConverters().addConverter(BigDecimalBigDecimalMorphiaConverter.class);
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
