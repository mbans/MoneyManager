package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.repo.AccountRepoImpl;
import com.lumar.moneymanager.util.TransactionBatchUploadOutcome;

public class TransactionServiceImpl implements TransactionService {

	// private TransactionRepoImpl tranRepo;
	private AccountRepoImpl repo;
	private TransactionValidator validator = new TransactionValidator();
	private TransactionGenerator tranGen = new TransactionGenerator();
	
	@Override
	public TransactionBatchUploadOutcome uploadTransactions(
			String transactions, String accountId, String fieldDelimeter) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public TransactionValidation uploadTransactions(Account account, String rawTransactionUpload) {
		TransactionValidation outcome = validator.preTransformValidation(account, rawTransactionUpload);
		Set<Transaction> realTransactions = tranGen.createTransactions(account, outcome.getValidTransactions());
		outcome.setTransasctions(realTransactions);
		return outcome;
	}
	
	@Override
	public void saveTransactions(List<Transaction> validTransactions) {
	}
}
