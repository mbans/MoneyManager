var app = angular.module('money-manager');

app.service('transactionService', function($http) {
	
	this.transactionFieldHeadings=[
	                     {name:"Date", mandatory:"true", type:"date"},
						 {name:"Description", mandatory:"true",type:"string"},
		  			     {name: "Amount", mandatory:"false", type:"money"},
		  			     {name: "Credit",mandatory:"false", type:"money"},
		  			     {name: "Debit",mandatory:"false", type:"money"},
		  			     {name: "Type",mandatory:"true", type:"money"},
		  		         {name: "Balance", mandatory:"true", type:"money"}
		  			    ];
	this.transactionFieldMap={};
	
	this.getAccountDetailsForUser = function(userId) {
		return [
		        this.accounts[0].details, 
		        this.accounts[1].details
		       ];
	}
	
	/**
	 * Upload transactions to the given account
	 */
	this.upload = function(account,transactions) {
		console.info("uploading tractions for " + account.name);
		var allData={};
		allData.accountName=account.name;
		allData.accountOwner=account.accountOwner;
		allData.transactions=transactions;
		
		return $http({
			   url: configuration.baseUrl+"/account/upload/", 
			   method:"POST",
			   data: JSON.stringify(allData),
			   headers: {"Content-Type":"application/json"}
			  });		
	}
	
	this.updateAccount = function(account) {
		console.info("Updating account " + account.name);
		return $http({
			   url: configuration.baseUrl+"/account/", 
			   method:"PUT",
			   data: JSON.stringify(account),
			   headers: {"Content-Type":"application/json"}
			  });
	}
	
	//Stubbed
	this.getTransactions = function(account) {
		console.log("Retrieving Transactions for AccountName["+account.name+"]");

		accountName=account.name;
		return $http.get(configuration.baseUrl+"/account/"+accountName
							+"/transactions/")	
					.then(
						function(response) {
							var transactions=response.data;
							console.log("Retrived " + transactions.length + " transactions for "+ accountName);
							return "";
						},
						function(httpError) {
							console.log("Error Retrieving accounts...");
						});
	}
	
	//Returns the entire account
	this.getAccountsForUser = function(userId) {
		toReturn=[];
		return this.accounts;
	}
	
	
	this.applyVerificationRules = function(account, rawTransactionLines) {
		var outcome = {};
		outcome.invalidTransactions=[];
		outcome.validTransactions=[];
		outcome.rawValidTransactions=[];
		outcome.rawTransactionRows=rawTransactionLines;
		outcome.transactionRows=[];
		
		var delimiter = account.delimiter;
		if(delimiter=="Tab") {
           delimiter="\t";
        }
        
        //Build up outcome.transactionRows
        for(i=0; i< outcome.rawTransactionRows.length; i++) {
    		outcome.transactionRows.push(outcome.rawTransactionRows[i].split(delimiter));
        }
        
		//Check Field Counts on the upload trans match those of the account
		this.applyFieldCountCheck(outcome, account.transactionHeadingOrdering); 
		this.applyFieldTypeCheck(outcome, account.transactionHeadingOrdering); 
		
		return outcome;
	}

	/**
	 * Checks that the number of fields on each transaction matches expected for the account
	 * 
	 * outcome - wrapper object holding all invalid and valid transactions
	 * accountHeadings -  an array of the account heading names.
	 */
	this.applyFieldCountCheck = function(outcome, accountHeadings) {
		expectedFieldCount=accountHeadings.length; 
		
		for(var i=0; i< outcome.rawTransactionRows.length; i++) {
			rawTransactionRow=outcome.rawTransactionRows[i];

			if(this.isInArray(rawTransactionRow, outcome.invalidTransactions)) {
				continue; //break out current iteration
			}

			transactionRow = outcome.transactionRows[i];
			
			if(transactionRow.length != expectedFieldCount) {
				invalidTran={};
				invalidTran.transaction=rawTransactionRow;
				invalidTran.reason="Field count should="+expectedFieldCount+ " actual="+transactionRow.length;
				outcome.invalidTransactions.push(invalidTran);
			}
			else {
				outcome.validTransactions.push(transactionRow);
				outcome.rawValidTransactions.push(rawTransactionRow);
			}
		}
	}
	
	/**
	 * Checks that all fields for all transactions are in accordance with the type of the heading.
	 * For example if date field then check the value provided is an actual valid date value
	 * 
	 * outcome - wrapper object holding all invalid and valid transactions
	 * accountHeadings -  an array of the account heading names.
	 * 
	 * returns the 
	 */
	this.applyFieldTypeCheck = function(outcome, accountHeadings) {
		expectedFieldCount=accountHeadings.length; 
		
		for(var i=0; i< outcome.rawTransactionRows.length; i++) {
			rawTransactionRow=outcome.rawTransactionRows[i];

			//Check if this is already an invalid row
			if(this.isInArray(rawTransactionRow, outcome.invalidTransactions)) {
				continue; //break out current iteration
			}
			
			transactionRow=outcome.transactionRows[i];
			
			//Now check each column type, and verify that it matches the value for this transaction
			var invalidTran=this.verifyTypesOnTransactionFields(transactionRow, accountHeadings);
		
			if(invalidTran.transaction == undefined) {
				if(!this.isInArray(transactionRow, outcome.validTransactions)) {
					outcome.validTransactions.push(transactionRow); 	
					outcome.rawValidTransactions.push(rawTransactionRow);
				}
			}
			else {
	            outcome.invalidTransactions.push(invalidTran);
			}
		}	
	}
	
	/**
	 * Given a transaction Row, will inspect all fields and verify they match the associated type of that field.
	 */
	this.verifyTypesOnTransactionFields = function(transactionRow, accountHeadings) {
		invalidTran={};
		for(var j=0; j<accountHeadings.length; j++) {
			transactionFieldValue=transactionRow[j]; //The acutal value of the field
			heading=this.getHeading(accountHeadings[j]);
			headingType=heading.type;
			
			if(headingType == "date" && !this.isValidDate(transactionFieldValue)) {
				invalidTran.reason="["+heading.name+"] contains an invalid date.";
				invalidTran.transaction=rawTransactionRow
				break; //this is now invalid
			}
//			else if (headingType == "money" && isValidMoney(transactionFieldValue)) { 
//				invalidTran.reason="["+heading.name+"] is an invalid monetry amount.";
//				invalidTran.transaction=rawTransactionRow
//				break;
//			}
		 }
		return invalidTran;
	}
	
	/**
	 * returns true if this is a valid date
	 */
	this.isValidDate = function(date) {
		return ((new Date(date)).toString() !== "Invalid Date") ? true : false;         
	}
	
//	this.isValidMoney(transactionFieldValue)) {
//		
//	}
//}
	
	
	/**
	 * Given a heading name, returns the corresponding heading entry, builds up a heading map in the process
	 */
	this.getHeading = function(headingName) {
		heading = this.transactionFieldMap[headingName]; 
		if(heading != undefined) {
			return heading;
		}
		
		//Retrieve it and cache it in the map
		for(var i=0; i< this.transactionFieldHeadings.length; i++) {
			heading = this.transactionFieldHeadings[i];
			if(heading.name == headingName) {
				this.transactionFieldMap[headingName] = heading;
				return heading;
			}
		}

		//No heading, something must be wrong, through an excpetion
		throw "Could not find registered Heading with the name "+headingName + " this must be an invalid heading.";
	}
	
	
	/***
	 * Returns true if the value is in the array
	 */
	this.isInArray = function(value, array) {
		index=$.inArray(value, array);
		return index != -1;
	}

});