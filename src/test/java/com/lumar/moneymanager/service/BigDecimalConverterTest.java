package com.lumar.moneymanager.service;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

public class BigDecimalConverterTest {
	
	@Test
	public void shouldRemoveCurrency() {
		BigDecimal converted = BigDecimalConverter.convert("$10.12");
		BigDecimal expected = new BigDecimal("10.12");
		Assert.assertEquals(expected,converted);
	}
	
	@Test
	public void shouldConvertNormal() {
		BigDecimal converted = BigDecimalConverter.convert("10.12");
		BigDecimal expected = new BigDecimal("10.12");
		Assert.assertEquals(expected,converted);
	}

	@Test
	public void shouldConvertToZero() {
		BigDecimal converted = BigDecimalConverter.convert("-");
		BigDecimal expected = new BigDecimal("0.00");
		Assert.assertEquals(expected,converted);
	}
	
	
}
