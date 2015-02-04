package com.lumar.moneymanager.service;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Rule;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.domain.TransactionAndRule;
import com.lumar.moneymanager.repo.RuleRepo;
import com.lumar.moneymanager.util.TransactionFieldConfig;
import com.lumar.moneymanager.util.TransactionFieldConfig.FieldOperator;
import com.lumar.moneymanager.util.TransactionFieldConfig.TransactionField;

public class RuleServiceImpl implements RuleService {
	
	private Logger LOG = LoggerFactory.getLogger(RuleServiceImpl.class);
	
	private RuleRepo repo; 
	
	public RuleServiceImpl(RuleRepo repo) {
		this.repo = repo;
	}
	
	/**
	 * Applies the user's rules to the given account
	 * @param account
	 * @return
	 */
	public List<TransactionAndRule> applyRules(Account account) {
		List<TransactionAndRule> toReturn = Lists.newArrayList();
		Set<Transaction> transactions = account.getTransactions();
		
		for(Transaction tran : transactions) {
			List<Rule> rules = getRulesForTransaction(account.getAccountOwner(), tran);
			toReturn.add(new TransactionAndRule(tran,rules));
		}

		return toReturn;
	}
	
	/**
	 * Returns the rules that the transaction matches
	 * @param tran
	 * @return
	 */
	private List<Rule> getRulesForTransaction(String user, Transaction tran) {
		List<Rule> matchingRules = Lists.newArrayList();
		List<Rule> allRules = getRules(user);
		
		for(Rule rule : allRules) {
			if(ruleIsApplicable(tran,rule)) {
				matchingRules.add(rule);
			}
		}
		return matchingRules;
	}

	@Override
	public void saveRules(String owner, List<Rule> rules) {
		LOG.info("Saving rules "+ rules);
		repo.saveRules(owner, rules);
	}
	
	@Override
	public List<Rule> getRules(String owner) {
		List<Rule> rules = repo.getRules(owner);
		LOG.info("Retrieved " + rules.size() + " rules for user " + owner);
		return rules;
	}
	
	/**
	 * 
	 * @param tran
	 * @param rule
	 * @return
	 */
	private boolean ruleIsApplicable(Transaction tran, Rule rule) {
		TransactionField field = TransactionFieldConfig.TransactionField.getFromFieldName(rule.getField());
		Object transactionValue = field.getFromTran(tran);

		//Operator for this rule
		FieldOperator operater =  TransactionFieldConfig.FieldOperator.getOperatorByName(rule.getOperator());
		return operater.matches(transactionValue, rule);
	}
	
}
