package com.lumar.moneymanager.integration;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.domain.TransactionEntry;
import com.lumar.moneymanager.domain.TransactionHeading;
import com.lumar.moneymanager.domain.User;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;
import com.mongodb.Mongo;

public class AccountIntegrationTest {
	
	private static AccountService accountService;	
	private static Mongo mongo;
	private static Morphia morphia;
	private static Datastore ds;
	private String SAMPLE_TRANSACTION="29 Dec 2014	POS	0643 28DEC14 , SAINSBURYS S/MKTS , LONDON GB	-	3.54	£518.31";
	
	@BeforeClass
	public static void setUp() throws Exception {
		accountService = new AccountServiceImpl("money-manager-test");
		mongo = new Mongo();
		morphia = new Morphia();
		morphia.mapPackage("com.lumar.moneymanager.domain");
		ds = morphia.createDatastore("money-manager-test");
		ds.delete(ds.createQuery(Transaction.class));
		ds.delete(ds.createQuery(Account.class));
		System.out.println("All records should be deleted...");
	}
	
	@After
	public void afterTest() {
		ds.delete(ds.createQuery(Transaction.class));
		ds.delete(ds.createQuery(Account.class));
		ds.delete(ds.createQuery(User.class));
	}
	
	@Test
	public void shouldCreateTransaction() {
		Transaction transaction = new Transaction("Acc1");
		transaction.addTransactionEntry(new TransactionEntry<Date>("date", new Date()));
		transaction.addTransactionEntry(new TransactionEntry<String>("type", "Debit"));
		transaction.addTransactionEntry(new TransactionEntry<String>("desc", "Ted Baker Glasgow"));
		transaction.addTransactionEntry(new TransactionEntry<Integer>("incoming", 0));
		transaction.addTransactionEntry(new TransactionEntry<Integer>("outgoing", 12));
		ds.save(transaction);
		
		//Retrieve
		Query<Transaction> query = ds.createQuery(Transaction.class);
		query.criteria("accountId").equal("Acc1");
		
		Transaction tran = query.asList().get(0);
				
		Assert.assertEquals(tran.getTransactionEntries().size()+"", 5+"");
	}
	
	@Test
	public void shouldCreateAccountViaService() {
		List<String> headings = new ArrayList<String>();
		headings.add("date");
		headings.add("description");
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "123456", SAMPLE_TRANSACTION, headings);
		
		//Retrieve
		Query<Account> query = ds.createQuery(Account.class);
		query.criteria("accountOwner").equal("martinbans");
		
		Account acc = query.asList().get(0);
		Assert.assertEquals(acc.getSortCode(), "123456");
		Assert.assertEquals(acc.getTransactionHeadingOrdering().size()+"", 2+"");
	}
	
	@Test
	public void shouldGetAccountByUsername() {
		//Given
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "", null, new ArrayList<String>());
		accountService.createAccount("martinbans", "Martin-Natwest", "RBS", "0012345678", "123456", null, new ArrayList<String>());
		
		//When
		Set<Account> accounts = accountService.getAccounts("martinbans");
		
		//Then
		Assert.assertTrue(accounts.size()==2);
	}
	
	@Test
	public void shouldGetAccountByAccountName() {
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "", null, new ArrayList<String>());
		Account account = accountService.getAccountByAccountName("Martin-RBS");
		Assert.assertTrue("martinbans".equals(account.getAccountOwner()));
	}
	
	@Test 
	public void shouldCreateUser() {
		//Create a new Account
		createAndSaveUser("martinbans", "Martin");
		
		//Retrieve
		Query<User> query = ds.createQuery(User.class);
		query.criteria("username").equal("martinbans");
		
		User retrievedUser = query.asList().get(0);
		Assert.assertEquals(retrievedUser.getFirstName(), "Martin");
	}
	
	private User createAndSaveUser(String userName, String firstname) {
		//Create a new Account
		User user = new User();
		user.setUsername("martinbans");
		user.setFirstName("Martin");
		ds.save(user);		
		return user;
	}
}
