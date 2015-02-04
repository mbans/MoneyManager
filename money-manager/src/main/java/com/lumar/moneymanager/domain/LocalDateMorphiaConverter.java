package com.lumar.moneymanager.domain;

import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDate;

import com.google.code.morphia.converters.SimpleValueConverter;
import com.google.code.morphia.converters.TypeConverter;
import com.google.code.morphia.mapping.MappedField;
import com.google.code.morphia.mapping.MappingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LocalDateMorphiaConverter extends TypeConverter implements SimpleValueConverter {

	public static final String DAY = "d";
	public static final String MONTH = "m";
	public static final String YEAR = "y";

	public LocalDateMorphiaConverter() {
		super(LocalDate.class);
	}

	@Override
	public final Object encode(Object value, MappedField optionalExtraInfo) throws MappingException {
		if (value == null) {
			return null;
		}

		if (!(value instanceof LocalDate)) {
			throw new RuntimeException("Did not expect " + value.getClass().getName());
		}

		LocalDate date = (LocalDate) value;
		DBObject obj = new BasicDBObject();
		obj.put(DAY,date.getDayOfWeek());
		obj.put(MONTH, date.getMonthOfYear());
		obj.put(YEAR, date.getYear());
		return obj;
	}

	@Override
	public LocalDate decode(Class targetClass, Object fromDBObject, MappedField optionalExtraInfo) {
		if (fromDBObject == null) {
			return null;
		}

		if (fromDBObject instanceof Map) {
			Map<String, Integer> map = (Map<String,Integer>)fromDBObject;
			return new LocalDate(map.get(YEAR),map.get(MONTH),map.get(DAY));			
		} 
		throw new RuntimeException("Did not expect " + fromDBObject.getClass().getName());
	}
}
