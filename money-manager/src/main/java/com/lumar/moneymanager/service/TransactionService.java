package com.lumar.moneymanager.service;

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
}
