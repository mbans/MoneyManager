package com.lumar.moneymanager.repo;

import java.util.Set;

import com.lumar.moneymanager.domain.Transaction;

public interface TransactionRepo {

	public Set<Transaction> getTransactionsByAccountName(String accountName);
	
}
