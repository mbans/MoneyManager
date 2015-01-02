package com.lumar.moneymanager.domain;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Property;

public abstract class BaseEntity {

	@Id
	@Property("id")
	protected ObjectId id;
	
	public BaseEntity() {
	}
	
	public ObjectId getId() {
		return id;
	}
	
	public void setId(ObjectId id) {
		this.id = id;
	}
}
