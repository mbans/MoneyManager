package com.lumar.moneymanager.domain;

import java.util.List;
import java.util.Set;

import com.google.code.morphia.annotations.Entity;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Entity
public class Account extends BaseEntity {

	private String bank;
	private String name;
	private String accountNum;
	private String sortCode;
	private String accountOwner; // the username for user
    private String sampleTransaction; 
    
    private Set<Transaction> transactions;
    
	//Related to the parsing of the account - maybe stored in seperate object?
	private List<String> transactionHeadingOrdering;
    private String delimiter; 
		
	public Account() {
		transactionHeadingOrdering = Lists.newArrayList();
		transactions = Sets.newHashSet();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getSortCode() {
		return sortCode;
	}

	public void setSortCode(String sortCode) {
		this.sortCode = sortCode;
	}

	public List<String> getTransactionHeadingOrdering() {
		return transactionHeadingOrdering;
	}

	public void addTransactionHeadingOrdering(String heading) {
		transactionHeadingOrdering.add(heading);
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	
	public void setTransactionHeadingOrdering(List<String> transactionHeadings) {
		this.transactionHeadingOrdering = transactionHeadings;
	}
	
	@Override
	public String toString() {
		return "Account[name="+name+", user="+this.accountOwner+"]";
	}
	
	public String getSampleTransaction() {
		return sampleTransaction;
	}
	
	public String getDelimiter() {
		if("Tab".equals(delimiter) || "tab".equals(delimiter)) {
			return "\t";
		}
		return delimiter;
	}
	
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public Set<Transaction> getTransactions() {
		return transactions;
	}
	
	public void setTransactions(Set<Transaction> transactions) {
		this.transactions = transactions;
	}

	public void addTransactions(Set<Transaction> transactionsToSave) {
		transactions.addAll(transactionsToSave);
	}
	
	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}
}
