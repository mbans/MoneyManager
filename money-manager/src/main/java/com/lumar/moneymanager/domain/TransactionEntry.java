package com.lumar.moneymanager.domain;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Property;

@Embedded
public class TransactionEntry<T> {
	
	private String fieldName;
	
	@Property("value")
	private T value;
	
	public TransactionEntry() {
	}
	
	public TransactionEntry(String fieldName, T value) {
		this.fieldName  = fieldName;
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
