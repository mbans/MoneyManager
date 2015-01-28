package com.lumar.moneymanager.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.domain._Transaction;
import com.lumar.moneymanager.util.TransactionBatchUploadOutcome;

public interface TransactionService {
	
	/**
	 * Uploads the given transactions to the specified account.
	 * @param account
	 * @param rawTransactionUpload
	 * @return - duplicate transaction rows
	 */
	public List<String> uploadTransactions(Account account, String rawTransactionUpload);

	/**
	 * Upload the given transactions to the specified account.
	 * @param account
	 * @param transactionsToUpload
	 * @return
	 */
	public List<String> uploadTransactions(Account account, List<String> transactionsToUpload);
	
	/**
	 * Retrieve all transactions for the given account
	 * @param accountOwner
	 * @param accountName
	 * @return
	 */
	public Set<Transaction> getTransactions(Account account);
}
