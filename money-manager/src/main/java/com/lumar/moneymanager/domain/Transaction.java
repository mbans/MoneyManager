package com.lumar.moneymanager.domain;

import java.util.HashSet;
import java.util.Set;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

@Entity
public class Transaction extends BaseEntity {
	
	private String accountId;
	
	@Embedded
	private Set<TransactionEntry<?>> transactionEntries;
	
	public Transaction() {
	}
	
	public Transaction(String accountId) {
		transactionEntries=new HashSet<TransactionEntry<?>>();
		this.accountId=accountId;
	}
	
	public void addTransactionEntry(TransactionEntry<?> entry) {
		transactionEntries.add(entry);
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public Set<TransactionEntry<?>> getTransactionEntries() {
		return transactionEntries;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}
