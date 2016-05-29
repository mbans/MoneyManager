package com.lumar.moneymanager;

import static spark.SparkBase.externalStaticFileLocation;

import com.lumar.moneymanager.rest.AccountRestService;

import static spark.SparkBase.setPort;

public class MoneyManagerBootstrapper {

	// TODO: Replace with a property
	private static String WEBAPP_LOC_WIND = "C:\\Users\\Martin\\git\\LumarProjects\\money-manager\\src\\main\\resources\\webapp\\public\\";
//	private static String WEBAPP_LOC_UNIX = "/Users/LumarMacy/git/LumarProjects/money-manager/src/main/resources/webapp/public";
	private static String WEBAPP_LOC_UNIX = "/Users/lumarmacy1/dev/git/MoneyManager/src/main/resources/webapp/public";
	private static String DATABASE_NAME = "money-manager";
	private AccountRestService restService;
	
	public static void main(String[] args) {
		MoneyManagerBootstrapper bootStrap = new MoneyManagerBootstrapper();
		bootStrap.init();
	}

	private void init() {
		setPort(5678);
		if(System.getProperty("os.name").startsWith("Windows")) {
			externalStaticFileLocation(WEBAPP_LOC_WIND);
		}
		else {
			externalStaticFileLocation(WEBAPP_LOC_UNIX);
		}
		
		restService = new AccountRestService(DATABASE_NAME);
		restService.init();
	}
}
