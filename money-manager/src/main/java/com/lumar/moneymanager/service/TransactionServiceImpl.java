package com.lumar.moneymanager.service;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.repo.AccountRepoImpl;
import com.lumar.moneymanager.util.TransactionBatchUploadOutcome;

public class TransactionServiceImpl implements TransactionService {
	
	private AccountRepoImpl repo;
	private TransactionUploadValidator validator;
	
	public TransactionBatchUploadOutcome uploadTransactions(String transactions, String accountId, String fieldDelimeter) {
//		Account account = repo.getAccount(accountId);
//		return validateUpload(account, transactions, fieldDelimeter);
		return null;
	}

	private TransactionBatchUploadOutcome validateUpload(Account account, String transactions, String fieldDelimiter) {
		//1. Validate we have correct number of columns as defined in Account
		
		//2. Validate that the 'types' of the columns
		
		//3. Check for duplicate transactions (add as warnings)
		return null;
	}
}
