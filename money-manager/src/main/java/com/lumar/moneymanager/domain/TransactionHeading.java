package com.lumar.moneymanager.domain;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class TransactionHeading<T> {
	
	private String name;
	private boolean mandatory;
	private Class<T> c; 
	
	public TransactionHeading() {
	}
	
	public TransactionHeading(String name, boolean mandatory, Class<T> c) {
		this.name = name;
		this.mandatory=mandatory;
		this.c = c;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public T getDefaultInstance() {
		return new  TransactionHeading.GenericInstanceCreator<T>().createContent(c);
	}
	
	private static class GenericInstanceCreator<U> {
		public U createContent(Class<U> c) { 
			try {
				return c.newInstance();
			}
			catch(Exception e) {
				throw new RuntimeException("cannot instantiate a default object for " + c.getName());
			}
		}
	}
}
