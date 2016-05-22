//Loads the money-manager modue
var module = angular.module('money-manager');


//Add the 'PasteController' as a 
module.controller('transactionController',  function ($scope, uiGridConstants, transactionService, accountService) {

	$scope.transactionService=transactionService;
	$scope.accountService=accountService;
	$scope.user=configuration.user; //Will be replaced by logged in user

	$scope.uploadOutcome=undefined;
	$scope.transactionText = "";
    $scope.delimiter="Tab";
    $scope.uploadIsValid=false;
    $scope.errors={};
    $scope.transactionText="";
    $scope.validTransactions=[];
    $scope.rawValidTransactions=[];
    $scope.invalidTransactions=[]; 
    $scope.rawTransactionRows=[];
    $scope.headings=[];
    $scope.rawTransactions="";
    $scope.userAccounts=[];
    $scope.selectedAccount={};
	$scope.chosenToUpload=false;
	$scope.chosenToView = false;
    
    
    //Add a watcher to the data, we will be continually re-evaluating if the user's transactions meet the basic validation rules
    //1. Match the correct number of fields
    //2. Date field is valid
    //3. Monetary anounts are valid
    $scope.$watch('transactionText', function() { 
    	if($scope.transactionText==undefined || $scope.transactionText=="") {
    		//Re-initialise
    	    $scope.validTransactions=[];
    	    $scope.rawValidTransactions=[];
    	    $scope.invalidTransactions=[]; 
    	    $scope.rawTransactionRows=[];
    		return;
    	}
    	$scope.uploadOutcome=undefined;
    	$scope.applyVerificationRules();
    });

    /**
     * User has chosen to upload transactions from view
     */
    $scope.uploadPending = function() {
    	$scope.chosenToUpload = true;
    	$scope.chosenToView = false;
    }
    
    $scope.viewTransactions = function() {
    	$scope.chosenToUpload = false;
    	$scope.chosenToView = true;
    }
    
    /**
     * Upload transactions
     */
    $scope.upload = function() {
    	console.log("Uploading " + $scope.rawValidTransactions.length + " transactions.");
        var promise=$scope.transactionService.upload($scope.selectedAccount,$scope.rawValidTransactions);
		promise.then(
			function(response) {
				duplicates = response.data;
				$scope.uploadOutcome={};
				$scope.uploadOutcome.uploadedCount = $scope.rawValidTransactions.length  - duplicates.length;
				$scope.uploadOutcome.duplicates = duplicates;
			},
			function() {
				bootbox.alert("Error uploading transactions, please attempt to reupload");
			}
		);
  }
    
   /**
    * Applies rules to the list of uploaded transactions, this allows real time feedback to the user on the transactions
    * This is a much more user friendly appoach to manually clicking "Verify" then having to hit "Upload"
    */ 
    $scope.applyVerificationRules = function() {
        $scope.rawTransactionRows=$scope.transactionText.split("\n");
    	var verificationOutcome=$scope.transactionService.applyVerificationRules($scope.selectedAccount, $scope.rawTransactionRows);
    	$scope.invalidTransactions = verificationOutcome.invalidTransactions;
    	$scope.validTransactions = verificationOutcome.validTransactions;
    	$scope.rawValidTransactions = verificationOutcome.rawValidTransactions;
    	console.log("Finished applying transaction verification rules");
    }
    
    
    $scope.verify = function() {
        //Initialise
        $scope.invalidRawTransactions={};
        $scope.validTransactions=[];
        $scope.rawTransactionRows=[];

        //The rows uploaded into the text area
        $scope.rawTransactionRows=$scope.transactionText.split("\n");

        if($scope.delimiter=="Tab") {
            $scope.delimiter="\t";
        }

        $scope.headings=$scope.selectedAccount.transactionHeadingOrdering.slice();
        
        for(i=0; i< $scope.rawTransactionRows.length; i++) {
            rawTransactionRow=$scope.rawTransactionRows[i];
            transactionRow=rawTransactionRow.split($scope.delimiter)
            
            transactionRow.push(category);
            fieldCount=transactionRow.length; //Number of fields on the transaction
            
            //Do we have matching number of fields to columns
            if(fieldCount != ($scope.headings.length)) {
                $scope.invalidTransactions.push(rawTransactionRow);
            }
            else {
            	$scope.validTransactions.push(transactionRow);
            }
        }
    }

    /**
     * Display the transactions for the selected account
     */
    $scope.transactionsGridOptions = {
    	    data:'selectedAccount.transactions', 				//transactions of the selected account 
    	    columnDefs: [
    	       { field: 'date', 
    	    	 displayName: 'Date',
    	    	 filter: {
        	        condition: uiGridConstants.filter.CONTAINS,
        	      }
    	       },
    	       
    	       { field: 'description', 
    	    	 displayName: 'Description', 
    	    	 enableColumnResizing: true,
    	    	 width: '40%',
    	    	 filter: {
    	             condition: uiGridConstants.filter.CONTAINS,
    	             placeholder: 'contains'
    	           }
    	       },
    	       
    	       { field: 'amount', displayName: 'Amount', 
    	    	 aggregationType: uiGridConstants.aggregationTypes.sum,
    	    	 filters: [{
	    		 		condition: uiGridConstants.filter.GREATER_THAN,
	             		placeholder: 'greater than'
	           		  },
	           		  {
	    		 		condition: uiGridConstants.filter.LESS_THAN,
	             		placeholder: 'less than'
	           		  }]
    	       },

    	       { field: 'credit', 
    	    	 displayName: 'Credit', 
    	    	 aggregationType: uiGridConstants.aggregationTypes.sum,
    	    	 filters: [{
	    		 		condition: uiGridConstants.filter.GREATER_THAN,
	             		placeholder: 'greater than'
	           		  },
	           		  {
	    		 		condition: uiGridConstants.filter.LESS_THAN,
	             		placeholder: 'less than'
	           		  }]    	       
    	       },
    	       
    	       { field: 'debit', 
    	    	 displayName: 'Debit', 
    	    	 aggregationType: uiGridConstants.aggregationTypes.sum,
    	    	 filters: [{
    	    		 		condition: uiGridConstants.filter.GREATER_THAN,
    	             		placeholder: 'greater than'
    	           		  },
    	           		  {
      	    		 		condition: uiGridConstants.filter.LESS_THAN,
      	             		placeholder: 'less than'
      	           		  }]
    	       },
    	       
    	    	 { field: 'runningBalance', 
    	    	   displayName: 'Balance',
      	    	   filters: [{
	    		 		condition: uiGridConstants.filter.GREATER_THAN,
	             		placeholder: 'greater than'
	           		  },
	           		  {
	    		 		condition: uiGridConstants.filter.LESS_THAN,
	             		placeholder: 'less than'
	           		  }]
    	    	 },
    	    	 
    	    	 { 
    	    	   field: 'category', 
        	       enableHiding: true,
      	    	   displayName: 'Category',
      	    	   filter: {
    	             condition: uiGridConstants.filter.CONTAINS,
    	             placeholder: 'contains'
    	           }
    	    	 }
    	       ],
    	    enableFiltering:true,
    	    enableHorizontalScrollbar: 2, 	//When needed
    		enableVerticalScrollbar: 2, 	//When needed
    	    //showGroupPanel: true   -- WARNING this causes strange behaviour with the grid heading  	    ,
    	    multiSelect : true,
    	    enableColumnResizing :true,
    	    plugins: [new ngGridFlexibleHeightPlugin()],
    	    showColumnFooter: true,
    	    showFooter: true
    };
    
    $scope.getTableStyle= function() {
    	   var rowHeight=30;
    	   var headerHeight=45;
    	   return {
    	      height: ($scope.selectedAccount.transactions.length * rowHeight + headerHeight) + "px"
    	   };
    	};
    
//		TODO: Eventually may need to use AJAX if/when we seperate Account/Transaction into seperate collections
//    	transactionPromise = $scope.transactionService.getTransactions($scope.selectedAccount)
//    						 	.then(
//									function(transactions) {
//										console.log("Retrived " + transactions.length + " transactions for "+ accountName);
//										$scope.selectedTransactions=transactions;
//									},
//									function(httpError) {
//										console.log("Error Retrieving transactions for selectedTransactions");
//								});
//    }

    /**
     * TODO: Do we need this
     */
    $scope.viewTransUploadArea = function() {
    	$scope.viewTrans=false;
    	$scope.viewTransactionUpload=true;
    	transactions = $scope.transactionService.getTransactions($scope.selectedAccount.id);
    	$scope.selectedTransactions=transactions;
    }
        
	//Retrieves user accounts for the current user
    $scope.getUserAccounts = function() {
    	console.log("In cotroller.getUserAccounts()");
		var userAccountsPromise=$scope.accountService.getUserAccounts($scope.user);
		userAccountsPromise.then(
			function(accounts) {
				$scope.userAccounts=accounts;
			},
			function(errorMessage) {
				console.log("Error retrieving Accounts for "+$scope.user + " error="+errorMessage);
			});
    }
    
    $scope.clearUpload = function() {
    	$scope.selectedTransactions=[];
        $scope.rawTransactions="";
    }
    
    $scope.uploadBoxEmpty = function() {
    	textAreaContent = $scope.rawTransactions;
    	return (textAreaContent == "");
    }
    
    /**
     * Place into a seperate service
     */
    function validateUploadTrasactions(rawTransactions) {
    	console.log("Validating uploaded Transactions ");
    	var transactionRows = [];
        var invalidRows = [];
        
    	expectedNumCols=$scope.selectedAccount.transactionHeadings.length;

    	var rows = rawTransactions.split(/[\n\f\r]/);
    	
    	//Validate content of row
    	rows.forEach(function (thisRow) {
             var row = thisRow.trim();
             if (row != '') {
                 var cols = row.split("\t");
                 
                 //TODO: Check that types match
                 if(accountHeadings.length != cols.length) {
                	 invalidRows.push(cols);
                 }
                 else {
                     transactionsRows.push(cols);
                 }
             }
         });
    	
    	return invalidRows.length == 0;
    }
    //Initialise
    $scope.getUserAccounts();
});