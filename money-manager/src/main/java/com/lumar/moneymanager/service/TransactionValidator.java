package com.lumar.moneymanager.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.lumar.moneymanager.domain.Account;

public class TransactionValidator {
	
	public static String INVALID_NUM_OF_COLUMNS = "Columns in upload does not match number of Headings"; 
	private static String TRANSACTION_COLUMN_DELIMITER = "\t";
	/**
	 * Validates the transactions against the given account
	 * @param account
	 * @param rawTransactions
	 * @return
	 */
	public TransactionValidation preTransformValidation(Account account, String uploadedTransactionText) {
		List<String> transactions = new ArrayList<String>(Arrays.asList(uploadedTransactionText.split("\n")));
		
		
		//1. Check if the number of columns in upload match the headings
		TransactionValidation validationWrapper = new TransactionValidation(transactions);
		validateTransactionColumns(validationWrapper, account.getTransactionHeadingOrdering(), transactions);
		//TODO: identifyDuplicateTransactions();
		return validationWrapper;
	}

	protected void validateTransactionColumns(TransactionValidation validationWrapper, List<String> headings, List<String> transactions) {
		Iterator<String> iterator = transactions.iterator();
		while(iterator.hasNext()) {
			String transaction = iterator.next();
			String[] columns = transaction.split(TRANSACTION_COLUMN_DELIMITER);
			if(columns.length != headings.size()){
				validationWrapper.addInvalidTransaction(INVALID_NUM_OF_COLUMNS, transaction);
				iterator.remove(); //Remove this item from the 'valid' list
			}
		}
	}
	
}
