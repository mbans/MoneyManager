package com.lumar.moneymanager.rest;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.repo.AccountRepo;
import com.lumar.moneymanager.repo.AccountRepoImpl;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;
import com.lumar.moneymanager.service.TransactionService;
import com.lumar.moneymanager.service.TransactionServiceImpl;

public class AccountRestService {
	
	private AccountService accountService;
	private TransactionService transactionService;

	private static Logger LOG = LoggerFactory.getLogger(AccountRestService.class);
	
	public AccountRestService(String databaseName) {
		AccountRepo accountRepo = null;
		accountRepo = new AccountRepoImpl(databaseName);
		this.accountService = new AccountServiceImpl(accountRepo);
		this.transactionService = new TransactionServiceImpl(accountRepo);
	}
	
	public void init() {
		
		get("/account/:accountOwner", (req, res) -> {
			String username = req.params("accountOwner");
			Set<Account> accounts = accountService.getAccountsForUser(username);
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
		 * upload transactions
		 */
		post("/account/upload/", (req,res) -> {
			//Raw content
			JSONObject o = new JSONObject(req.body());
			
			String accountName = o.getString("accountName");
			String accountOwner = o.getString("accountOwner");
			JSONArray transactionJson = o.getJSONArray("transactions");
			List<String> transactions = Lists.newArrayList();

			LOG.info("Attempting to upload "+transactions.size() + " transactions.");
			
			for (int i=0;i<transactionJson.length();i++){ 
				 transactions.add(transactionJson.get(i).toString());
			} 
			
			Account account = accountService.getAccountByOwnerAndName(accountOwner, accountName);
			List<String> duplicates = transactionService.uploadTransactions(account, transactions);
			LOG.info("Uploaded " + (transactions.size()-duplicates.size()) + " transactions, "+ duplicates.size() + " duplicates detected");
			return new Gson().toJson(duplicates);
		});
	}
}
