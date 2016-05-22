package com.lumar.moneymanager.rest;

import static spark.Spark.get;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesRestService {
	
	private static Logger LOG = LoggerFactory.getLogger(RulesRestService.class);
	
	public RulesRestService(String databaseName) {
	}
	
	public void init() {
		
		get("/api/rules/", (req, res) -> {
			LOG.info("Test");
			return "bla";
		});
	}
}
