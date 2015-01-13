package com.lumar.moneymanager.service;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DATE_HEADER;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DESC_HEADER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lumar.moneymanager.domain.Transaction;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DATE_HEADER;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DESC_HEADER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lumar.moneymanager.domain.Transaction;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DATE_HEADER;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_TYPE_HEADER;
import static com.lumar.moneymanager.service.TransactionGenerator.TRAN_DESC_HEADER;


public class TransactionGeneratorTest {
	
	private TransactionGenerator tranGen = new TransactionGenerator();
	
	@Test
	public void shouldCreateTransaction() {
		String accountName = "Martin-RBS";
		
		List<String> tranCols = new ArrayList<String>(){{
			add(TransactionGenerator.TRAN_DATE); 
			add(TransactionGenerator.TRAN_TYPE); 
			add(TransactionGenerator.TRAN_DESC);
		}};
		
		String[] tranValues = {"22-Dec", "POS", "Ted Baker"};
		
		//When
		Transaction tran = tranGen.createTransaction(accountName, tranValues, tranCols);
		
		//Then
		String desc = tran.getTransactionFieldValue(TRAN_DESC_HEADER);
		String type = tran.getTransactionFieldValue(TRAN_TYPE_HEADER);

		Assert.assertEquals("Ted Baker", desc);
		Assert.assertEquals("POS", type);

	}
}
