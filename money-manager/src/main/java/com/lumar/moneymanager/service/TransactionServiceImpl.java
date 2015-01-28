package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.repo.AccountRepo;

public class TransactionServiceImpl implements TransactionService {
	
	private static Logger LOG = LoggerFactory.getLogger(AccountServiceImpl.class);
	private AccountRepo accountRepo;
	private TransactionGenerator tranGen = new TransactionGenerator();
	
	public TransactionServiceImpl(AccountRepo accountRepo) {
		this.accountRepo = accountRepo;
	}
	
	@Override
	public List<String> uploadTransactions(Account account, String rawTransactionUpload) {
		List<String> transactionsToUpload = Lists.newArrayList(rawTransactionUpload.split("\n"));
		List<String> duplicates = getDuplicateTransactions(account, transactionsToUpload);
		
		//Save down only the non duplicates
		if(!duplicates.isEmpty()) {
			transactionsToUpload.removeAll(duplicates);
		}
		
		if(transactionsToUpload.isEmpty()) {
			LOG.info("All transactions are duplicates, nothing to upload");
			return duplicates;
		}
		
		//TransactionValidation outcome = validator.preTransformValidation(account, rawTransactionUpload);
		Set<Transaction> transToSave = tranGen.createTransactions(account, transactionsToUpload);
		saveTransactions(account, transToSave);
		return duplicates;
	}
	
	@Override
	public List<String> uploadTransactions(Account account, List<String> transactionsToUpload) {
		List<String> duplicates = getDuplicateTransactions(account, transactionsToUpload);
		
		//Save down only the non duplicates
		if(!duplicates.isEmpty()) {
			transactionsToUpload.removeAll(duplicates);
		}
		
		if(transactionsToUpload.isEmpty()) {
			LOG.info("All transactions are duplicates, nothing to upload");
			return duplicates;
		}
		
		//TransactionValidation outcome = validator.preTransformValidation(account, rawTransactionUpload);
		Set<Transaction> transToSave = tranGen.createTransactions(account, transactionsToUpload);
		saveTransactions(account, transToSave);
		return duplicates;
	}
	
	@Override
	public Set<Transaction> getTransactions(Account account) {
		return account.getTransactions();
	}
	
	
	/**
	 * Given a list of transactions to upload for a given account, return the duplicates
	 * @param account
	 * @param transactionsToUpload
	 * @return
	 */
	private List<String> getDuplicateTransactions(Account account, List<String> transactionsToUpload) {
		Set<Transaction> currentTransactions = account.getTransactions();
		Set<Transaction> toUpload = tranGen.createTransactions(account, transactionsToUpload);
		
		Set<Transaction> dups = Sets.intersection(currentTransactions, toUpload);
		Map<Transaction, String> tranMap = tranGen.createTransactionMap(account,transactionsToUpload);
		
		List<String> toReturn = Lists.newArrayList();
		for(Transaction dup : dups) {
			toReturn.add(tranMap.get(dup));
		}
		return toReturn;
	}

	private void saveTransactions(Account account, Set<Transaction> validTransactions) {
		account.addTransactions(validTransactions);
		accountRepo.updateAccount(account);
	}
}
