package com.lumar.moneymanager.domain;

import java.util.List;

import com.google.common.collect.Lists;

public class TransactionAndRule {
	
	private Transaction transaction;
	
	private List<Rule> rules;
	
	public TransactionAndRule() {
		rules = Lists.newArrayList();
	}

	public TransactionAndRule(Transaction tran, List<Rule> rules) {
		this.transaction = tran;
		this.rules=rules;
	}

	public Transaction getTransaction() {
		return transaction;
	}
	
	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	public List<Rule> getRules() {
		return rules;
	}
	
	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}
}
