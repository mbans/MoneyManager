package com.lumar.moneymanager.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.joda.time.LocalDate;

import com.google.code.morphia.annotations.Entity;

@Entity
public class Transaction extends BaseEntity {

	public final static BigDecimal ZERO_BALANCE = new BigDecimal(0.00);
	
	private String accountName;
	
	private LocalDate date;
	
	private String type;
	
	private String description;
	
	private BigDecimal amount = ZERO_BALANCE;

	private BigDecimal credit = ZERO_BALANCE;
	
	private BigDecimal debit = ZERO_BALANCE;
	
	private BigDecimal runningBalance = ZERO_BALANCE;

	private String dateFormat;

	public Transaction() {
		accountName="";
		date = new LocalDate();
		type="";
		description="";
	}

	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getCredit() {
		return credit;
	}

	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}

	public BigDecimal getDebit() {
		return debit;
	}

	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	
	public BigDecimal getRunningBalance() {
		return runningBalance;
	}
	
	public void setRunningBalance(BigDecimal runningBalance) {
		this.runningBalance = runningBalance;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null) {
			return false;
		}
		Transaction tran = (Transaction)obj;
		
		return 
				tran.getAmount().equals(this.getAmount()) && 
				tran.getCredit().equals(this.getCredit()) && 
				tran.getDebit().equals(this.getDebit()) && 
				tran.getDescription().equals(this.getDescription()) && 
				tran.getRunningBalance().equals(this.getRunningBalance()) && 
				tran.getAccountName().equals(this.getAccountName());
//				&& tran.getDate().equals(this.getDate()
	}
	
	public int hashCode() {
		return 
			this.getAmount().hashCode() +
			this.getCredit().hashCode() +
			this.getDebit().hashCode() + 
			this.getDescription().hashCode() +
			this.getRunningBalance().hashCode() +
			this.getAccountName().hashCode();
//			+ this.getDate().hashCode();

	}
}
