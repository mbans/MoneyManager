package com.lumar.moneymanager.integration;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.common.collect.Lists;
import com.lumar.moneymanager.domain.BigDecimalBigDecimalMorphiaConverter;
import com.lumar.moneymanager.domain.Rule;
import com.lumar.moneymanager.repo.RuleRepoImpl;
import com.lumar.moneymanager.service.RuleService;
import com.lumar.moneymanager.service.RuleServiceImpl;
import com.mongodb.Mongo;

public class RuleIntegrationTest {
	
	private static RuleService ruleService;	
	private static Mongo mongo;
	private static Morphia morphia;
	private static Datastore ds;
	private static String TEST_DATABASE = "money-manager-test";
	
	
	@BeforeClass
	public static void setUp() throws Exception {
		ruleService = new RuleServiceImpl(new RuleRepoImpl(TEST_DATABASE));
		mongo = new Mongo();
		morphia = new Morphia();
		morphia.mapPackage("com.lumar.moneymanager.domain");
		morphia.getMapper().getConverters().addConverter(BigDecimalBigDecimalMorphiaConverter.class);
		
		ds = morphia.createDatastore(TEST_DATABASE);
		ds.delete(ds.createQuery(Rule.class));
		System.out.println("All rules deleted");
	}
	
	@After
	public void afterTest() {
		ds.delete(ds.createQuery(Rule.class));
	}
	
	@Test
	public void shouldSaveRules() {
		//Construct rules
		List<Rule> rulesToSave = Lists.newArrayList();
		Rule r1 = new Rule();
		r1.setName("GroceriesRule");
		r1.setField("Groceries");
		r1.setOperator("equals");
		r1.setValue("Tesco");
		r1.setCategory("Groceries");
		r1.setOwner("martin");
		
		Rule r2 = new Rule();
		r2.setName("TravelRule");
		r2.setField("Travel");
		r2.setOperator("contains");
		r2.setValue("TFL");
		r2.setCategory("Travel");
		r2.setOwner("martin");

		rulesToSave.add(r1);
		rulesToSave.add(r2);
		
		//When
		ruleService.saveRules("martin",rulesToSave);
		List<Rule> rules = ruleService.getRules("martin");
		
		//Then
		Assert.assertEquals(2, rules.size());
	}
	
}
