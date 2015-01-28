package com.lumar.moneymanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;

public class TransactionGenerator {
			
	public Set<Transaction> createTransactions(Account account, List<String> transactions) {
		List<String> headings = account.getTransactionHeadingOrdering();
		Set<Transaction> toReturn = new HashSet<Transaction>(); 
		
		for(String transaction : transactions) {
			String[] transactionValues = transaction.split(account.getDelimiter());
			Transaction tran = createTransaction(transactionValues , headings); 
			toReturn.add(tran);
		}
		return toReturn;
	}
	
	/**
	 * Returns 
	 * @param account
	 * @param transactions
	 * @return
	 */
	public Map<Transaction,String> createTransactionMap(Account account, List<String> transactions) {
		Map<Transaction,String> toReturn = Maps.newHashMap();
		for(String transactionStr : transactions) {
			Transaction tran = createTransaction(account, transactionStr);
			toReturn.put(tran,transactionStr);
		}
		return toReturn;
	}
	
	private Transaction createTransaction(Account account, String transactionStr) {
		String[] transactionValues = transactionStr.split(account.getDelimiter());
		return createTransaction(transactionValues , account.getTransactionHeadingOrdering()); 
	}
	
	/**
	 * Inspects the credit and debit and assign amount accordingly
	 * @param tran
	 */
	private void setAmount(Transaction tran) {
		//Only do stuff if amount has a zero value
		if(Transaction.ZERO_BALANCE.compareTo(tran.getAmount()) != 0) {
			return;
		}
		
		//Amount is a Credit
		BigDecimal amount = null;
		if(Transaction.ZERO_BALANCE.compareTo(tran.getCredit()) == 0) { //Credit is ZERO, therefore must be a debit
			amount = tran.getDebit().negate().setScale(2, RoundingMode.CEILING);
		}
		else {	//Debit is ZERO, therefore must be a credit
			amount = tran.getCredit().setScale(2, RoundingMode.CEILING);
		}
		tran.setAmount(amount);
	}

	/**
	 * Creates a single Transaction
	 * @param tran
	 * @param headings
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Transaction createTransaction(String[] transactionValues, List<String> headingNamesForAccount) {
		Transaction tran = new Transaction();

		for(int i=0; i < transactionValues.length; i++) {
			String headingName=headingNamesForAccount.get(i);
			String tranFieldValue=transactionValues[i];
			
			TransactionHeadingDef headingDefinition = TransactionHeadingDef.getEntry(headingName);
			
			//Assigns the value to the given transaction, including all required casting
			headingDefinition.assignField(tran, tranFieldValue);
		}
		setAmount(tran);
		return tran;
	}
	
	/**
	 * @param account
	 * @param rawTransactionUpload
	 * @return
	 */
	public Set<Transaction> createTransactions(Account account, String rawTransactionUpload) {
		List<String> transactions = Arrays.asList(rawTransactionUpload.split("\n"));
		return createTransactions(account,transactions);
	}
	
	
	/**
	 * Defines the transactionFields along with how to convert each from string to the reqired format
	 * @author Martin
	 *
	 */
	public enum TransactionHeadingDef {
		DATE("Date",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yy");
					Date date = sdf.parse(rawValue);
					tran.setDate(date);
				}
				catch(Exception e) {
					throw new RuntimeException("Could not convert Date["+rawValue+"] must be in format dd-MMM-yy");				}
			}
		},
		TYPE("Type",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setType(rawValue);
			}
		},
		DESCRIPTION("Description",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setDescription(rawValue);
			}
		},
		AMOUNT("Amount",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setAmount(BigDecimalConverter.convert(rawValue));
			}
		},
		CREDIT("Credit",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setCredit(BigDecimalConverter.convert(rawValue));
			}
		},
		DEBIT("Debit",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setDebit(BigDecimalConverter.convert(rawValue));
			}
		},
		BALANCE("Balance",true) {
			@Override
			public void assignField(Transaction tran, String rawValue) {
				tran.setRunningBalance(BigDecimalConverter.convert(rawValue));
			}
		};
		
		public abstract void assignField(Transaction tran, String rawValue);
		
		private String name;
		
		private boolean mandatory;
		
		private TransactionHeadingDef(String name, boolean mandatory) {
			this.name = name;
			this.mandatory = mandatory;
		}
		
		public static TransactionHeadingDef getEntry(String name) {
			for(TransactionHeadingDef heading : TransactionHeadingDef.values()) {
				if(heading.getName().equals(name)) {
					return heading;
				}
			}
			throw new RuntimeException("No heading called ["+name+"]");
		}

		public String getName() {
			return name;
		}
		
		public boolean isMandatory() {
			return mandatory;
		}
	}
}
