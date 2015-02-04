package com.lumar.moneymanager.domain;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;

public class TransactionTest {
	
	@Test
	public void shouldBeEqual() {
		Transaction t1 = createTransaction("1", 
										   "Desc1", 
										   "Type1", 
										   new BigDecimal(0.0), 
										   new BigDecimal(1.0), 
										   new BigDecimal(2.0), 
										   new BigDecimal(3.0));
		
		Transaction t2 = createTransaction("1", 
				   "Desc1", 
				   "Type1", 
				   new BigDecimal(0.0), 
				   new BigDecimal(1.0), 
				   new BigDecimal(2.0), 
				   new BigDecimal(3.0));

		Assert.assertEquals(t1, t2);
	}
	
	@Test
	public void shouldBeUnEqual() {
		Transaction t1 = createTransaction("1", 
				   "Desc1", 
				   "Type1", 
				   new BigDecimal(0.0), 
				   new BigDecimal(1.0), 
				   new BigDecimal(2.0), 
				   new BigDecimal(3.0));
		
		Transaction t2 = createTransaction("1", 
				   "Desc1", 
				   "Type1", 
				   new BigDecimal(0.0), 
				   new BigDecimal(1.0), 
				   new BigDecimal(2.0), 
				   new BigDecimal(3.1));
		
		Assert.assertNotSame(t1, t2);
	}
	
	private Transaction createTransaction(String id, String desc, String type, BigDecimal amount, BigDecimal credit, BigDecimal debit, BigDecimal balance) {
		Transaction t = new Transaction();
		t.setAccountName(id);
		t.setDescription(desc);
		t.setType(type);
		t.setAmount(amount);
		t.setCredit(credit);
		t.setDebit(debit);
		t.setRunningBalance(balance);
		return t;
	}
}
