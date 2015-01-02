package com.lumar.moneymanager.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Acts as a wrapper to caputre the outcome of uploading trasactions
 */
public class TransactionBatchUploadOutcome {
	
	private boolean success;
	
	private Set<TransactionUploadResult> invalid;
	
	private Set<TransactionUploadResult> warnings;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Set<TransactionUploadResult> getInvalid() {
		return invalid;
	}

	public void setInvalid(Set<TransactionUploadResult> invalid) {
		this.invalid = invalid;
	}

	public Set<TransactionUploadResult> getWarnings() {
		return warnings;
	}

	public void setWarnings(Set<TransactionUploadResult> warnings) {
		this.warnings = warnings;
	}	
	
	public void addTransactionWarning(TransactionUploadResult result) {
		if(warnings == null) {
			warnings = new HashSet<TransactionUploadResult>();
		}
		warnings.add(result);
	}
	
	public void addInvalidTransaction(TransactionUploadResult result) {
		if(invalid == null) {
			invalid = new HashSet<TransactionUploadResult>();
		}
		invalid.add(result);

	}
}
