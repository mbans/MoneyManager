package com.lumar.moneymanager.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;

@Entity
public class Transaction extends BaseEntity {
	
	private String accountId;
	
	@Embedded
	private Map<String, TransactionEntry<?>> transactionEntries;
	
	public Transaction() {
	}
	
	public Transaction(String accountId) {
		transactionEntries=new HashMap<String, TransactionEntry<?>>();
		this.accountId=accountId;
	}
	
	public void addTransactionEntry(TransactionEntry<?> entry) {
		transactionEntries.put(entry.getFieldName(), entry);
	}
	
	public String getAccountId() {
		return accountId;
	}
	
	public Map<String,TransactionEntry<?>> getTransactionEntries() {
		return transactionEntries;
	}
	
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	/**
	 * Given a 
	 * @param heading
	 * @return
	 */
	public <T> T getTransactionFieldValue(TransactionHeading<T> heading) {
		TransactionEntry<T> entry = (TransactionEntry<T>)transactionEntries.get(heading.getName());
		if(entry == null) {
			return null;
		}
		return entry.getValue();
	}
	
	
}
