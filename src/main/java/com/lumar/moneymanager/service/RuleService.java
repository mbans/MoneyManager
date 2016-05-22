package com.lumar.moneymanager.service;

import java.util.List;

import com.google.code.morphia.Key;
import com.lumar.moneymanager.domain.Rule;

public interface RuleService {

	public void saveRules(String user, List<Rule> rule);	
	
	public List<Rule> getRules(String user);
}
