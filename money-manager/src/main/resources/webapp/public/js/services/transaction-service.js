var app = angular.module('money-manager');

app.service('transactionService', function() {
	
	
/*				
		var headings={};
		headings[0] = '{name:"Transaction Date" mandatory:"true"}';
*/		
		
	this.transactionFieldHeadings=[
	                     {name:"Transaction Date", mandatory:"true"},
						 {name:"Transaction Description", mandatory:"true"},
		  			     {name: "Transaction Amount", mandatory:"false"},
		  			     {name: "Transaction Credit",mandatory:"false"},
		  			     {name: "Transaction Debit",mandatory:"false"},
		  			     {name: "Transaction Type",mandatory:"true"},
		  		         {name: "Running Balance", mandatory:"true"},
		  		         {name: "N/A", mandatory:"false"}
		  			    ];
	
	
/*	this.accounts=[
	    {
	    id:"1",
		details: 	{  
					 id:"1",
					 bank:"RBS",
					 name:"Marty-RBS", 
					 accountNum:"123456789",
					 sortCode:"301144"
		 },
		 transactionHeadings:[{name:"date",type:"date"},
			                  {name:"type",type:"string"},
			                  {name:"descripton",type:"string"},
			                  {name:"incomingAmount",type:"int"},
			                  {name:"outgoingAmount",type:"string"},
			                  {name:"balance",type:"string"},
			                  {name:"category", type:"string"}
			                  ]
	     ,
		 transactions :
				[
				 {date: "01 Dec 2014", type: "POC", description:"POS	0643 20DEC14 , TED BAKER , GLASGOW GB", incoming:"-", outgoing:"50.00", balance:"�2,540.86", category:"shopping"},
				 {date: "01 Dec 2014", type: "POC", description:"POS	0643 20DEC14 , Tesco, GLASGOW GB", incoming:"-", outgoing:"100.00", balance:"�2,540.86", category:"shopping"},
		         {date: "03 Dec 2014", type: "POC", description:"POS	0643 20DEC14 , TFL",incoming:"-",outgoing:"2:00",balance:"�2540.86", category:"travel"},
		         {date: "04 Dec 2014", type: "POC", description:"POS	0643 20DEC14 , Mortgage",incoming:"-",outgoing:"1000",balance:"�2540.86", category:"mortgage"},
		         {date: "04 Dec 2014", type: "POC", description:"POS	0643 20DEC14 , Salary",incoming:"679.00",outgoing:"-",balance:"�2540.86", category:"salary"}
		         ]
		},
		
		//2nd account
		{
			details: {
			 id:"2",
			 bank:"Natwest",
			 name:"Marty-Natwest", 
			 accountNum:"11111111",
			 sortCode:"222222"
		 },
		 
		 transactionHeadings:[{name:"date",type:"date"},
				              {name:"descripton",type:"string"},
				              {name:"amount",type:"int"},
				              {name:"category",type:"string"}
				             ],
			 transactions :
					[
					 {date: "01 Dec 2014", description:"POS	0643 20DEC14 , TED BAKER , GLASGOW GB",amount:"-89.95",category:"shopping"},
			         {date: "03 Dec 2014", description:"POS	0643 20DEC14 , TFL",amount:"-2:00",category:"travel"},
			         {date: "04 Dec 2014", description:"POS	0643 20DEC14 , Mortgage",amount:"-1000",category:"mortgage"},
			         {date: "04 Dec 2014", description:"POS	0643 20DEC14 , Salary",amount:"697.00",category:"salary"}
					]
		}
	];
	
*/	
	/*this.getTransactionFieldHeadings = function() {
		return {headings: [{name:"Transaction Date" mandatory:"true"}, 
		         {name:"Transaction Description", manadatory:"true"},
			     {name: "Transaction Amount", mandatory:"false"},
			     {name: "Transaction Credit",mandatory:"false"},
			     {name: "Transaction Debit",mandatory:"false"},
			     {name:  "Transaction Type",mandatory:"true"},
		         {name: "Running Balance", mandatory:"true"}
			     ]
				};
	}*/
	
	
	this.getAccountDetailsForUser = function(userId) {
		return [
		        this.accounts[0].details, 
		        this.accounts[1].details
		       ];
	}
	
	//Stubbed
	this.getTransactions = function(accountId) {
		console.log("Retrieving Transactions for AccountId["+accountId+"]");
		toReturn=[];
		if(accountId=="1") {
			toReturn = this.accounts[0].transactions;
		} 
		else {
			toReturn = this.accounts[1].transactions;
		}
		console.log("Returned ["+toReturn.length+"] transactions");
		return toReturn;
	}
	
	//For a given user get all account details
	this.getAccountsAndTransactions = function(user) {
		console.log("Retrieving account details for User["+user+"]");
		return this.accounts;
	}
	
	//Returns the entire account
	this.getAccountById = function(accountId) {
		if(accountId=="1") {
			return this.accounts[0];
		}
		return this.accounts[1];
	}
	
	//Returns the entire account
	this.getAccountsForUser = function(userId) {
		toReturn=[];
		return this.accounts;
	}
	
});