package com.lumar.moneymanager.rest;

import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;
import static spark.Spark.delete;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;
import com.lumar.moneymanager.service.TransactionService;
import com.lumar.moneymanager.service.TransactionServiceImpl;
import com.lumar.moneymanager.service.TransactionValidation;

public class AccountRestService {

	private AccountService accountService = new AccountServiceImpl();
	
	private TransactionService transactionService = new TransactionServiceImpl();
		
	private static Logger LOG = LoggerFactory.getLogger(AccountRestService.class);
	
	public void init() {
			
		/**
		 * Get Account by account owner
		 */
		get("/account/:accountOwner", (req, res) -> {
			String username = req.params("accountOwner");
			Set<Account> accounts = accountService.getAccounts(username);
			LOG.info("Returned [{}] accounts for [{}]",accounts,username);
			return new Gson().toJson(accounts);
		});
		
		
		/**
		 * Get Account by account name
		 */
		get("/account/:accountName/transactions/", (req,res) -> {
			String accountName = req.params("accountName");
			LOG.info("Retrieving Transactions for Account ["+accountName+"]");	
			return "";
		});
		
		/**
		 * Update account
		 */
		put("/account/", (req,res) -> {
			String accountJson = req.body();					
			LOG.info("Updating Account from JSON [{}]", accountJson);
			Account account = new Gson().fromJson(accountJson, Account.class);
			return accountService.updateAccount(account);
 		});
		
		/**
		 * Create account
		 */
		post("/account/", (req,res) -> {
			String accountJson = req.body();					
			LOG.info("Saving Account from JSON [{}]", accountJson);
			Account account = new Gson().fromJson(accountJson, Account.class);
			return accountService.saveAccount(account);
 		});
		
		/**
		 * Update account
		 */
		put("/account/", (req,res) -> {
			String accountJson = req.body();					
			LOG.info("Updating Account from JSON [{}]", accountJson);
			Account account = new Gson().fromJson(accountJson, Account.class);
			return accountService.updateAccount(account);
 		});
		
		/**
		 * Delete Account
		 */
		delete("/account/", (req,res) -> {
			Account account = new Gson().fromJson(req.body(), Account.class);
			LOG.info("Deleting Account [{}]", account.getName());
			accountService.delete(account);
			return true;
		});
		
		
		/**
		 * Retrieve transactions for the given account
		 */
		post("/account/:accountName/transactions/", (req,res) -> {
			//Raw content
			String rawTransactionUpload = req.body();
			String accountName = req.params("accountName");
			Account account = accountService.getAccountByAccountName(accountName);
			TransactionValidation transactionOutcome = transactionService.uploadTransactions(account,rawTransactionUpload);
			return new Gson().toJson(transactionOutcome);
		});
	}
}
