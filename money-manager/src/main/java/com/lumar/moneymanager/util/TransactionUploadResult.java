package com.lumar.moneymanager.util;

public class TransactionUploadResult {

	private String rawTransaction;
	
	private String message; 
	
	public TransactionUploadResult(String rawTransaction, String message) {
		setMessage(message);
		setRawTransaction(rawTransaction);
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getRawTransaction() {
		return rawTransaction;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setRawTransaction(String rawTransaction) {
		this.rawTransaction = rawTransaction;
	}
}
