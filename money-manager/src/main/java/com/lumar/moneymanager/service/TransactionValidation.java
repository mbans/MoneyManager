package com.lumar.moneymanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lumar.moneymanager.domain._Transaction;

public class TransactionValidation {
	
	private Map<String, List<String>> invalidTransactions; 
	private List<String> validTransactions; 
	private Set<_Transaction> transasctions;

	public TransactionValidation(List<String> transactions) {
		validTransactions = transactions;
		invalidTransactions = new HashMap<String,List<String>>();
	}
	
	public List<String> getValidTransactions() {
		return validTransactions;
	}
	
	public void setValidTransactions(List<String> validTransactions) {
		this.validTransactions = validTransactions;
	}
	
	public Map<String, List<String>> getInvalidTransactions() {
		return invalidTransactions;
	}
	
	public void setInvalidTransactions(Map<String, List<String>> invalidTransactions) {
		this.invalidTransactions = invalidTransactions;
	}
	
	public void addInvalidTransaction(String reason, String transactionText) {
		List<String> invalidTrans = this.invalidTransactions.get(reason);
		if(invalidTrans == null) {
			invalidTrans = new ArrayList<String>();
			invalidTransactions.put(reason, invalidTrans);
		}
		invalidTrans.add(transactionText);
	}
	
	public Set<_Transaction> getTransasctions() {
		return transasctions;
	}
	
	public void setTransasctions(Set<_Transaction> transasctions) {
		this.transasctions = transasctions;
	}
}
