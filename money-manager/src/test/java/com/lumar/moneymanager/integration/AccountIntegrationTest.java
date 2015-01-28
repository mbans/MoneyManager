package com.lumar.moneymanager.integration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.common.collect.Lists;
import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.BigDecimalBigDecimalMorphiaConverter;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.domain.TransactionEntry;
import com.lumar.moneymanager.domain.User;
import com.lumar.moneymanager.domain._Transaction;
import com.lumar.moneymanager.repo.AccountRepo;
import com.lumar.moneymanager.repo.AccountRepoImpl;
import com.lumar.moneymanager.service.AccountService;
import com.lumar.moneymanager.service.AccountServiceImpl;
import com.lumar.moneymanager.service.TransactionGenerator;
import com.lumar.moneymanager.service.TransactionGenerator.TransactionHeadingDef;
import com.lumar.moneymanager.service.TransactionService;
import com.lumar.moneymanager.service.TransactionServiceImpl;
import com.mongodb.Mongo;

public class AccountIntegrationTest {
	
	private static TransactionService transactionService;
	private static AccountService accountService;	
	private static AccountRepo accountRepo;
	private static Mongo mongo;
	private static Morphia morphia;
	private static Datastore ds;
	private String SAMPLE_TRANSACTION="29 Dec 2014	POS	0643 28DEC14 , SAINSBURYS S/MKTS , LONDON GB	-	3.54	£518.31";
	private static String TEST_DATABASE = "money-manager-test";
	private static TransactionGenerator tranGen; 
	
	private static List<String> HEADINGS = Lists.asList(
			TransactionHeadingDef.DATE.getName(),
			new String[]{
				TransactionHeadingDef.DESCRIPTION.getName(),
				TransactionHeadingDef.TYPE.getName(),
				TransactionHeadingDef.CREDIT.getName(),
				TransactionHeadingDef.DEBIT.getName(),
				TransactionHeadingDef.BALANCE.getName(),
			}
		);
	
	@BeforeClass
	public static void setUp() throws Exception {
		accountRepo = new AccountRepoImpl(TEST_DATABASE);
		accountService = new AccountServiceImpl(accountRepo);
		transactionService = new TransactionServiceImpl(accountRepo);
		mongo = new Mongo();
		morphia = new Morphia();
		morphia.mapPackage("com.lumar.moneymanager.domain");
		morphia.getMapper().getConverters().addConverter(BigDecimalBigDecimalMorphiaConverter.class);
		tranGen = new TransactionGenerator();
		
		ds = morphia.createDatastore("money-manager-test");
		ds.delete(ds.createQuery(_Transaction.class));
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
	public void shouldCreateAccountWithTransaction() {
		//Given
		Account account = createAccount("martin", "martin-rbs","123456", "789", "RBS", "Tab");
		account.setTransactionHeadingOrdering(HEADINGS);

		//Construct acc+tran
		String upload = "1	Desc	Type	-	12.00	$12.12";	
		Set<Transaction> transactions = tranGen.createTransactions(account, upload);
		account.addTransactions(transactions);
		accountService.saveAccount(account);
		
		//Retrieve
		Account acc = accountService.getAccountByOwnerAndName("martin", "martin-rbs");
		
		Assert.assertEquals(acc.getSortCode(), "789");
		Assert.assertEquals(1, acc.getTransactions().size());
	}
	
	
	@Test 
	public void shouldReturnDuplicateTransaction() {
		//Create Account with one transaction
		String upload = "22-Dec-83	Desc	Type	-	12.00	$12.12";	
		Account account = createAccount("martin", "martin-rbs","123456", "789", "RBS", "Tab");
		account.setTransactionHeadingOrdering(HEADINGS);
		transactionService.uploadTransactions(account, upload);
		
		//Upload a duplicate
		String newUpload = 	  "22-Dec-83	Desc	Type	-	12.00	$12.12\n"
							+ "22-Jan-08	NewDesc	NewType	-	12.00	$12.12";
		List<String> dups = transactionService.uploadTransactions(account, newUpload);
		
		//Then - we should have one duplicate
		Assert.assertEquals(1, dups.size());
		Assert.assertEquals(2, account.getTransactions().size());
	}
	
	@Test
	public void shouldDeleteAccount() {
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "", null, new ArrayList<String>());
		Account account=accountService.getAccountByOwnerAndName("martinbans", "Martin-RBS");
		Assert.assertTrue(account != null);
		
		accountService.delete(account);
		account=accountService.getAccountByOwnerAndName("martinbans", "Martin-RBS");
		Assert.assertTrue(account == null);
	}
	
	@Test
	public void shouldUpdateAccount() {
		
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "", null, new ArrayList<String>());
		Account account = accountService.getAccountByOwnerAndName("martinbans", "Martin-RBS");
		account.setBank("New Bank");
		
		//When
		accountService.updateAccount(account);

		//Then
		Account updatedAccount=accountService.getAccountByOwnerAndName("martinbans", "Martin-RBS");
		Assert.assertEquals("New Bank", updatedAccount.getBank());
	}
	
	@Test
	public void shouldCreateTransaction() {
		_Transaction transaction = new _Transaction("Acc1");
		transaction.addTransactionEntry(new TransactionEntry<Date>("date", new Date()));
		transaction.addTransactionEntry(new TransactionEntry<String>("type", "Debit"));
		transaction.addTransactionEntry(new TransactionEntry<String>("desc", "Ted Baker Glasgow"));
		transaction.addTransactionEntry(new TransactionEntry<Integer>("incoming", 0));
		transaction.addTransactionEntry(new TransactionEntry<Integer>("outgoing", 12));
		ds.save(transaction);
		
		//Retrieve
		Query<_Transaction> query = ds.createQuery(_Transaction.class);
		query.criteria("accountId").equal("Acc1");
		
		_Transaction tran = query.asList().get(0);
				
		Assert.assertEquals(tran.getTransactionEntries().size()+"", 5+"");
	}
	
	@Test
	public void shouldCreateAccount() {
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
		Set<Account> accounts = accountService.getAccountsForUser("martinbans");
		
		//Then
		Assert.assertTrue(accounts.size()==2);
	}
	
	@Test
	public void shouldGetAccountByAccountName() {
		accountService.createAccount("martinbans", "Martin-RBS", "RBS", "0012345678", "", null, new ArrayList<String>());
		Account account = accountService.getAccountByOwnerAndName("martinbans", "Martin-RBS");
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
	
//	private Transaction createTransaction(String name, String desc, String type,  BigDecimal credit, BigDecimal debit, BigDecimal balance, BigDecimal amount) {
//		Transaction
//		Transaction t = new Transaction();
//		t.setAccountName(name);
//		t.setDescription(desc);
//		t.setType(type);
//		t.setAmount(amount.setScale(2, RoundingMode.CEILING));
//		t.setCredit(credit.setScale(2, RoundingMode.CEILING));
//		t.setDebit(debit.setScale(2, RoundingMode.CEILING));
//		t.setRunningBalance(balance.setScale(2, RoundingMode.CEILING));
//		return t;
//	}

	public static Account createAccount(String owner, String name, String accountNum, String sortCode, String bank, String delimiter) {
		Account a = new Account();
		a.setAccountOwner(owner);
		a.setName(name);
		a.setAccountNum(accountNum);
		a.setSortCode(sortCode);
		a.setBank(bank);
		a.setDelimiter(delimiter);
		return a;
	}
}
