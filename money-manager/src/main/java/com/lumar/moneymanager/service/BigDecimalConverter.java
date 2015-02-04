package com.lumar.moneymanager.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalConverter {

	public static BigDecimal convert(String value) {
		String newValue = value.replaceAll(",","").trim();
		if(isNonDigit(value)) {
			return new BigDecimal("0.00").setScale(2, RoundingMode.CEILING);
		}
		
		newValue = newValue.replaceAll("[^\\d.]", "");
		return new BigDecimal(newValue).setScale(2, RoundingMode.CEILING);
	}
	
	private static boolean isNonDigit(String value) {
		//If no digits at all then we'll convert to 0.00
		int count=0;
		for(Character c: value.toCharArray()) {
			if(Character.isDigit(c)) { 
				count++;
			}
		}
		return (count==0);
	}
}
