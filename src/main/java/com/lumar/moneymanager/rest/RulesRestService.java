package com.lumar.moneymanager.rest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Spark;

public class RulesRestService {
	
	private static Logger LOG = LoggerFactory.getLogger(RulesRestService.class);
	
	public RulesRestService(String databaseName) {
	}

	public void init() {

		Spark.get("/api/rules/", (req, res) -> {
			LOG.info("Test");
			return "bla";
		});
	}
}
