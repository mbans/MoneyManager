package com.lumar.moneymanager.domain;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class TransactionHeading {
	
	private String name;
	private Class<?> type;
	private Integer order;
	
	public TransactionHeading() {
	}
	
	public TransactionHeading(String name, Class<?> c, Integer order) {
		this.name = name;
		this.type = c;
		this.order=order;
	}
	
	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}
	
	public Integer getOrder() {
		return order;
	}
}
