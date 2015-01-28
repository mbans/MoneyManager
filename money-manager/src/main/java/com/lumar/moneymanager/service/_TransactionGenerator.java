package com.lumar.moneymanager.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.domain.TransactionEntry;
import com.lumar.moneymanager.domain.TransactionHeading;
import com.lumar.moneymanager.domain._Transaction;

public class _TransactionGenerator {
	
	public static String TRAN_DATE = "Transaction Date";
	public static String TRAN_TYPE = "Transaction Type";
	public static String TRAN_DESC = "Transaction Description";
	public static String TRAN_AMOUNT = "Transaction Amount";
	public static String TRAN_CREDIT = "Transaction Credit";
	public static String TRAN_DEBIT = "Transaction Debit";
	public static String BALANCE = "Running Balance";
	public static String CATEGORY = "Category";
	
	public static final TransactionHeading<Date> TRAN_DATE_HEADER = new TransactionHeading<Date>(TRAN_DATE,true,Date.class);
	public static final TransactionHeading<String> TRAN_TYPE_HEADER = new TransactionHeading<String>(TRAN_TYPE,true,String.class);
	public static final TransactionHeading<String> TRAN_DESC_HEADER = new TransactionHeading<String>(TRAN_DESC,true,String.class);
	public static final TransactionHeading<String> TRAN_AMOUNT_HEADER = new TransactionHeading<String>(TRAN_AMOUNT,true,String.class);
	public static final TransactionHeading<String> TRAN_CREDIT_HEADER = new TransactionHeading<String>(TRAN_CREDIT,true,String.class);
	public static final TransactionHeading<String> TRAN_DEBIT_HEADER = new TransactionHeading<String>(TRAN_DEBIT,true,String.class);
	public static final TransactionHeading<String> BALANCE_HEADER = new TransactionHeading<String>(BALANCE,true,String.class);
	public static final TransactionHeading<String> CATEGORY_HEADER = new TransactionHeading<String>(CATEGORY,true,String.class);
		
	public static final Map<String, TransactionHeading<?>> headings = new HashMap<String,TransactionHeading<?>>() {{
		//TODO: Support for Strings only at the moment
		put(TRAN_DATE,TRAN_TYPE_HEADER);
		put(TRAN_TYPE,TRAN_TYPE_HEADER);
		put(TRAN_DESC,TRAN_DESC_HEADER);
		put(TRAN_AMOUNT,TRAN_AMOUNT_HEADER);
		put(TRAN_CREDIT,TRAN_CREDIT_HEADER);
		put(TRAN_DEBIT,TRAN_DEBIT_HEADER);
		put(BALANCE,BALANCE_HEADER);
		put(CATEGORY,CATEGORY_HEADER);
	}};
	
//	public Set<_Transaction> createTransactions(Account account, List<String> transactions) {
//		List<String> headings = account.getTransactionHeadingOrdering();
//		Set<_Transaction> toReturn = new HashSet<_Transaction>(); 
//		
//		for(String transaction : transactions) {
//			String[] tran = transaction.split(account.getDelimiter());
////			toReturn.add(createTransaction(account.getName(), tran,headings));
//		}
//		return toReturn;
//	}
	
	/**
	 * Creates a single Transaction
	 * @param tran
	 * @param headings
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <R> _Transaction createTransaction(String[] transactionValues, 
												 List<String> headingNamesForAccount) {
		_Transaction tran = new _Transaction();
		
		for(int i=0; i < transactionValues.length; i++) {
			String transactionColumnValue=transactionValues[i];
			//TransactionHeading<R> heading = (TransactionHeading<R>)headings.get(headingNamesForAccount.get(i));
			//addTranEntry(tran, heading, transactionColumnValue);
		}
		return tran;
	}
	
	/**
	 * R is the target type for our value
	 * @param tran
	 * @param heading
	 * @param tranColValue
	 */
	private <R> void addTranEntry(_Transaction tran, TransactionHeading<R> heading, String tranColValue) {
		//TODO: Cast the 'tranColumnValue' to type T 
		//For now everything will be stored as string though;
		R tranValue = (R)tranColValue;  //This will only work for String, need to put support for casting to Date/Integer/BigDecimal etc
		TransactionEntry<R> tranEntry = new TransactionEntry<R>(heading.getName(), tranValue);
		tran.addTransactionEntry(tranEntry);
	}
	
	/**
	 * @param account
	 * @param rawTransactionUpload
	 * @return
	 */
	public Set<_Transaction> createTransactions(Account account, String rawTransactionUpload) {
		List<String> transactions = Arrays.asList(rawTransactionUpload.split("\n"));
//		return createTransactions(account,transactions);
		return null;
	}
}
