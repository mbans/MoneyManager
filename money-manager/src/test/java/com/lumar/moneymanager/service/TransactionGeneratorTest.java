package com.lumar.moneymanager.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.lumar.moneymanager.domain.Account;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.integration.AccountIntegrationTest;
import com.lumar.moneymanager.service.TransactionGenerator.TransactionHeadingDef;


public class TransactionGeneratorTest {
	
	private TransactionGenerator tranGen = new TransactionGenerator();
	
	private static List<String> tranCols = new ArrayList<String>(){{
		add(TransactionHeadingDef.DATE.getName()); 
		add(TransactionHeadingDef.TYPE.getName()); 
		add(TransactionHeadingDef.DESCRIPTION.getName()); 
		add(TransactionHeadingDef.CREDIT.getName());
		add(TransactionHeadingDef.DEBIT.getName());
		add(TransactionHeadingDef.BALANCE.getName());
	}};
	

	@Test
	public void shouldCreateTransaction() {
		Account account = AccountIntegrationTest.createAccount("martin", "martin-rbs","123456", "789", "RBS", "Tab");
		
		account.setTransactionHeadingOrdering(tranCols);
		
		String rawTransactionUpload="22-Dec-83	POS	TedBaker	12.00	-	£1212.00\n"+
									"26-Jan-99	ATM	Tesco	-	12.09	£112.00";
		
		//When
		Set<Transaction> trans = tranGen.createTransactions(account, rawTransactionUpload);
		
		Iterator<Transaction> iter = trans.iterator();
		
		//Then
		assertEquals(2,trans.size());
		Transaction t = iter.next();
		assertEquals("ATM", t.getType());
		assertEquals("Tesco", t.getDescription());
		assertEquals(new BigDecimal("0.00"), t.getCredit());
		assertEquals(new BigDecimal("12.09"), t.getDebit());
		assertEquals(new BigDecimal("112.00"), t.getRunningBalance());
		assertEquals(new BigDecimal("-12.09"), t.getAmount());
		
		t = iter.next();
		assertEquals("POS", t.getType());
		assertEquals("TedBaker", t.getDescription());
		assertEquals(new BigDecimal("12.00"), t.getCredit());
		assertEquals(new BigDecimal("0.00"), t.getDebit());
		assertEquals(new BigDecimal("1212.00"), t.getRunningBalance());
		assertEquals(new BigDecimal("12.00"), t.getAmount());
	}
}
