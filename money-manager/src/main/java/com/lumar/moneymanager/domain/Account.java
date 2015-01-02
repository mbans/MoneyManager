package com.lumar.moneymanager.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;

@Entity
public class Account extends BaseEntity {

	private String bank;
	private String name;
	private String accountNum;
	private String sortCode;
	private String accountOwner; // the username for user

	@Property("cardNumbers")
	private Set<String> cardNumbers;

	@Embedded
	private Set<TransactionHeading> transactionHeadings;

	public Account() {
		transactionHeadings = new HashSet<TransactionHeading>();
		cardNumbers = new HashSet<String>();
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

	public Set<TransactionHeading> getTranHeadings() {
		return transactionHeadings;
	}

	public void addTransactonHeading(TransactionHeading heading) {
		transactionHeadings.add(heading);
	}

	public String getAccountOwner() {
		return accountOwner;
	}

	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}

	public void addCardNumber(String cardNumber) {
		cardNumbers.add(cardNumber);
	}

	public void removeCardNumber(String cardNumber) {
		cardNumbers.remove(cardNumber);
	}

	public void setCardNumbers(Set<String> cardNumbers) {
		if (cardNumbers != null) {
			this.cardNumbers = cardNumbers;
		}
	}
	
	public void setTransactionHeadings(
			Set<TransactionHeading> transactionHeadings) {
		this.transactionHeadings = transactionHeadings;
	}
	
	@Override
	public String toString() {
		return "Account[name="+name+", user="+this.accountOwner+"]";
	}
}
