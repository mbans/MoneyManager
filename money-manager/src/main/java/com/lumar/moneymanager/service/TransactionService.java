package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.util.TransactionBatchUploadOutcome;

public interface TransactionService {
	
	/**
	 * Uploads the given transactions for the given accountId
	 * @param transactions - tabulated
	 * @param accountId
	 * @param fieldDelimeter
	 * @return
	 */
	public TransactionBatchUploadOutcome uploadTransactions(String transactions, String accountId, String fieldDelimeter);
	
	/**
	 * Returns the transactions for a given account name
	 * @param accountName
	 * @return
	 */
//	public Set<Transaction> getTransactionsByAccountName(String accountName);
		
	/**
	 * Saves the transactions
	 * @param validTransactions
	 */
	public void saveTransactions(List<Transaction> validTransactions);

	/**
	 * Uploads the given transactions to the specified account.
	 * @param account
	 * @param rawTransactionUpload
	 * @return
	 */
	public TransactionValidation uploadTransactions(Account account, String rawTransactionUpload);
}
