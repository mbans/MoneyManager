package com.lumar.moneymanager.rest;

import static spark.Spark.get;
import static spark.Spark.post;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;

public class AccountRestService {

	private AccountService accountService = new AccountServiceImpl();
	
	private static Logger LOG = LoggerFactory.getLogger(AccountRestService.class);
	
	public void init() {
	
		get("/account/ping", (req, res) -> "pong");

		get("/account/:accountOwner", (req, res) -> {
			String username = req.params("accountOwner");
			Set<Account> accounts = accountService.getAccounts(username);
			LOG.info("Returned [{}] accounts for [{}]",accounts,username);
			return new Gson().toJson(accounts);
		});
		
		post("/account/", (req,res) -> {
			String accountJson = req.body();					
			LOG.info("Saving Account from JSON [{}]", accountJson);
			Account account = new Gson().fromJson(accountJson, Account.class);
			return accountService.saveAccount(account);
 		});
	}

}
