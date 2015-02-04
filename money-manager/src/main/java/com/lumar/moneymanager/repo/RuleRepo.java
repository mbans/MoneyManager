package com.lumar.moneymanager.repo;

import java.util.List;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Rule;

public interface RuleRepo {

	public void saveRules(String user, List<Rule> rules);
	
	public List<Rule> getRules(String owner);
}
