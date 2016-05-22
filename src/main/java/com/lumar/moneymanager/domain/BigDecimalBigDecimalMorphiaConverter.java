package com.lumar.moneymanager.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.google.code.morphia.converters.SimpleValueConverter;
import com.google.code.morphia.converters.TypeConverter;
import com.google.code.morphia.mapping.MappedField;
import com.google.code.morphia.mapping.MappingException;

public class BigDecimalBigDecimalMorphiaConverter extends TypeConverter implements SimpleValueConverter {

	public BigDecimalBigDecimalMorphiaConverter() {
		super(BigDecimal.class);
	}
	
	@Override
	protected boolean isSupported(Class<?> c, MappedField optionalExtraInfo) {
		return BigDecimal.class.isAssignableFrom(c);
	}
	
	@Override
	public Object encode(Object value, MappedField optionalExtraInfo) {
	if(value == null)
	    return null;
	return value.toString();
	}
	
	@Override
	public Object decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) throws MappingException {
		if (fromDBObject == null) return null;  
		return new BigDecimal(fromDBObject.toString()).setScale(2, RoundingMode.CEILING);
	}
}
