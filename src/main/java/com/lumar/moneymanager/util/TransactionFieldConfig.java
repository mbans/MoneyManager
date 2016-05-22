package com.lumar.moneymanager.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import com.google.common.collect.Lists;
import com.lumar.moneymanager.domain.Rule;
import com.lumar.moneymanager.domain.Transaction;
import com.lumar.moneymanager.service.BigDecimalConverter;


public class TransactionFieldConfig {
	
	public enum TransactionField {
	
		DATE("Date",true,"date") {
				@Override
				public Object getFromTran(Transaction t) { return t.getDate(); }
				
				@Override
				public void assignToTran(Transaction tran, String rawValue) {
					try {
						DateTimeFormatter dtf = new DateTimeFormatterBuilder()
					    .appendDayOfMonth(2)
					    .appendLiteral('-')
					    .appendMonthOfYearShortText()
					    .appendLiteral('-')
					    .appendYear(2,4)
					    .toFormatter().withLocale(Locale.UK);
						
						LocalDate date = dtf.parseLocalDate(rawValue);
						tran.setDate(date);
					}
					catch(Exception e) {
						throw new RuntimeException("Could not convert Date["+rawValue+"] must be in format dd-MMM-yy");	
					}
				}
		},
		
		DESCRIPTION("Description",true,"string") {
			@Override
			public Object getFromTran(Transaction t) { return t.getDescription(); }
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {
				t.setDescription(rawValue);
			}
		},
		
		AMOUNT("Amount",false,"money") {
			@Override
			public Object getFromTran(Transaction t) { return t.getAmount(); }
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {				
				t.setAmount(BigDecimalConverter.convert(rawValue));
			}
		},

		CREDIT("Credit",false,"money") {
			@Override
			public Object getFromTran(Transaction t) { 
				return t.getCredit(); 
			}
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {
				t.setCredit(BigDecimalConverter.convert(rawValue));
			}
		},

		DEBIT("Debit",false,"money") {
			@Override
			public Object getFromTran(Transaction t) { return t.getDebit(); }
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {
				t.setDebit(BigDecimalConverter.convert(rawValue));
			}		
		},

		TYPE("Type",false,"type") {
			@Override
			public Object getFromTran(Transaction t) { return t.getType(); }
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {
				t.setType(rawValue);
			}
		},

		BALANCE("Balance",false,"balance") {
			@Override
			public Object getFromTran(Transaction t) { return t.getRunningBalance(); }
			
			@Override
			public void assignToTran(Transaction t, String rawValue) {
				t.setRunningBalance(BigDecimalConverter.convert(rawValue));
			}

		};
		
		private String fieldName;
		
		private boolean mandatory;
		
		private String type;
		
		public abstract Object getFromTran(Transaction t);

		public abstract void assignToTran(Transaction t, String rawValue);
		
		private TransactionField(String fieldName, boolean isMandatory, String type) {
			this.fieldName=fieldName;
			this.mandatory=isMandatory;
			this.type=type;
		}
		
		/**
		 * Returns the TransactionField from the name
		 * @param name
		 * @return
		 */
		public static TransactionField getFromFieldName(String name) {
			for(TransactionField field : values()) {
				if (field.getFieldName().equals(name)) {
					return field;
				}
			}
			return null;
		}
		
		public String getFieldName() {
			return fieldName;
		}
		
		public String getType() {
			return type;
		}		
		
		public boolean isMandatory() {
			return mandatory;
		}
	}
	
	/**
	 * Defines the different operators that can be applied, the "apply" method contains logic for applying the operator to a value given a Rule
	 *
	 */
	public enum FieldOperator {
		LESS_THAN("less than",Lists.newArrayList("money")) {
			public boolean matches(Object transactionFieldValue, Rule rule) {
				if(isMoney(transactionFieldValue)) {
					BigDecimal tranValue = (BigDecimal)transactionFieldValue;
					BigDecimal ruleValue = new BigDecimal(rule.getValue());
					return tranValue.compareTo(ruleValue) < 0;
				}
				return false;
			};
		},
		
		
		LARGER_THAN("larger than",Lists.newArrayList("money")) {
			@Override
			public boolean matches(Object transactionFieldValue, Rule rule) {
				if(isMoney(transactionFieldValue)) {
					BigDecimal tranValue = (BigDecimal)transactionFieldValue;
					BigDecimal ruleValue = new BigDecimal(rule.getValue());
					return tranValue.compareTo(ruleValue) > 0;
				}
				return false;
			}
		},
		
		
		
		EQUALS("equals",Lists.newArrayList("money","string","date")) {
			@Override
			public boolean matches(Object transactionFieldValue, Rule rule) {
				String ruleValue = rule.getValue(); 
				
				if(isMoney(transactionFieldValue)) {
					BigDecimal tranValue = (BigDecimal)transactionFieldValue;
					BigDecimal ruleBd = new BigDecimal(ruleValue);
					return tranValue.compareTo(ruleBd) == 0;
				}

				else if(transactionFieldValue instanceof String) {
					return ((String)transactionFieldValue).equals(ruleValue);
				}
				
				else if(transactionFieldValue instanceof LocalDate) {
					LocalDate transactionDate =  (LocalDate)transactionFieldValue;
					LocalDate ruleDate= new LocalDate(ruleValue);
					return transactionDate.equals(ruleDate);
				}
				return false;
			}	
		},
		
		STARTS_WITH("starts with",Lists.newArrayList("string")) {
			@Override
			public boolean matches(Object transactionFieldValue, Rule rule) {
				if(transactionFieldValue instanceof String) {
					return ((String)transactionFieldValue).startsWith(rule.getValue());
				}
				return false;
			}
		},
		
		ENDS_WITH("ends with",Lists.newArrayList("string")) {
			@Override
			public boolean matches(Object transactionFieldValue, Rule rule) {
				if(transactionFieldValue instanceof String) {
					return ((String)transactionFieldValue).endsWith(rule.getValue());
				}
				return false;
			}
		},
		
		CONTAINS("contains",Lists.newArrayList("string")) {
			@Override
			public boolean matches(Object transactionFieldValue, Rule rule) {
				if(transactionFieldValue instanceof String) {
					return ((String)transactionFieldValue).contains(rule.getValue());
				}
				return false;
			}
		};

		
		private String description;

		private List<String> applicableTypes;
		
		/**
		 * Returns true if the given rule is applicable for the value (derived from a transaction), works by applying the logic for the given operator on the arguments
		 * @param value
		 * @param rule
		 * @return
		 */
		public abstract boolean matches(Object transactionFieldValue, Rule rule);
 
		
		protected boolean isMoney(Object value) {
			if(value instanceof BigDecimal) {
				return true;
			}
			
			//Check if in valid monetary format
			if(value instanceof String) {
				return ((String)value).matches("-?\\d+(\\.\\d+)?");
			}
			return false;
		}


		private FieldOperator(String description, List<String> types) {
			this.description = description;
			this.applicableTypes = types;
		}
		
		public List<String> getApplicableTypes() {
			return applicableTypes;
		}
		
		public String getDescription() {
			return description;
		}
		
		public static List<FieldOperator> getOperatorsForType(String type) {
			List<FieldOperator> toReturn = Lists.newArrayList();
			for(FieldOperator fo : values()) {
				if(fo.getApplicableTypes().contains(type)) {
					toReturn.add(fo);
				}
			}
			return toReturn;
		}


		public static FieldOperator getOperatorByName(String operator) {
			for(FieldOperator fo : values()) {
				if(operator.equals(fo.getDescription())) {
					return fo;
				}
			}
			return null;
		}
	}
	
/*	public static BigDecimal convertToBd(Object o) {
			if(o instanceof String) {
				if((String)o).))
					return new BigDecimal
				
				//match a number with optional '-' and decimal.
			}
			
			if(o instanceof BigDecimal) {
				return true
			}
				
		}
*/	
}
