package com.lumar.moneymanager.core;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.externalStaticFileLocation;

import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;

public class MoneyManagerBootstrapper {

	// TODO: Replace with a property
	private static String WEBAPP_LOC = "C:\\Users\\Martin\\workspaces\\sandbox\\money-manager\\src\\main\\resources\\webapp\\public\\";

	private AccountService accountService = new AccountServiceImpl();

	public static void main(String[] args) {
		MoneyManagerBootstrapper bootStrap = new MoneyManagerBootstrapper();
		bootStrap.addRoutes();
	}

	private void addRoutes() {
		externalStaticFileLocation(WEBAPP_LOC);
		
		get("/account/ping", (req, res) -> "pong");
		
		get("/account/:username", (req, res) -> {
			String username = req.params(":username");
			return accountService.getAccounts(username);
		});
		
		//TODO: Return ExceptionMessages if errored
		post("/account/", (req, res) -> {
					String accountJson = req.params("account");
					Gson gson = new Gson();
					Account newAccount = gson.fromJson(accountJson, Account.class);	
					
					try {
						accountService.saveAccount(newAccount);
						return true;
					} catch (Exception e) {
						return false;
					}
				});

		/*
		 * public Key<Account> createAccount(String user, String accountName,
		 * String bank, String accountNum, String sortCode, Set<String>
		 * cardNumbers, Set<TransactionHeading> headings) {
		 */

	}
}
