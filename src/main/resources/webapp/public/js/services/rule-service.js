var app = angular.module('money-manager');

app.service('ruleService', function($http,transactionService) {
	
	//ToDo - move this into a config-service retrieved from server
	
	this.fieldNames= transactionService.transactionFieldHeadings;
	
	this.operators = {"string":["contains","equals","starts with","ends with"],
					  "money":["larger than","less than", "equals"],
					  "date" :["before","after"]
					 };
	
	this.getOperators = function(fieldName) {
		//Get type
		for(var i=0; i<this.fieldNames.length; i++) {
			field=this.fieldNames[i];
			if(field.name==fieldName) {
				return this.operators[field.type];
			}
		}
		return [];
	}
	
});