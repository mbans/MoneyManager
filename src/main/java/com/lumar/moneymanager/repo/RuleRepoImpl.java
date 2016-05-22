package com.lumar.moneymanager.repo;

import java.util.List;

import com.google.code.morphia.query.Query;
import com.lumar.moneymanager.domain.Rule;

public class RuleRepoImpl extends AbstractRepo implements RuleRepo {

	public RuleRepoImpl(String databaseName) {
		super(databaseName);
	}

	@Override
	public void saveRules(String user, List<Rule> rules) {
		//Delete Existing rules
		List<Rule> allRules = getRules(user);
		for(Rule r : allRules) {
			getDs().delete(r);
		}
		
		//New Rules
		for(Rule r : rules) {
			getDs().save(r);
		}
	}

	@Override
	public List<Rule> getRules(String owner) {
		Query<Rule> query = getDs().createQuery(Rule.class);
		query.criteria("owner").equal(owner);
		return query.asList();
	}
}
