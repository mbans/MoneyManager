package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;

public interface TransactionService {
	
	/**
	 * Uploads the given transactions to the specified account.
	 */
	List<String> uploadTransactions(Account account, String rawTransactionUpload);

	/**
	 * Upload the given transactions to the specified account.
	 */
	List<String> uploadTransactions(Account account, List<String> transactionsToUpload);
	
	/**
	 * Retrieve all transactions for the given account
	 */
	Set<Transaction> getTransactions(Account account);
}
