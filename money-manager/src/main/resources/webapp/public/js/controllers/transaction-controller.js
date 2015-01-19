//Loads the money-manager modue
var module = angular.module('money-manager');


//Add the 'PasteController' as a 
module.controller('transactionController',  function ($scope, transactionService, accountService) {

    $scope.chosenToUpload=false;
	$scope.chosenToView = false;
	
	$scope.accountName="";
	$scope.transactionText = "";
    $scope.delimiter="Tab";
    $scope.uploadIsValid=false;
    $scope.errors={};

    $scope.transactionText="";
    $scope.validTransactions=[];
    $scope.invalidTransactions=[]; 
    $scope.rawTransactionRows=[];
    
    $scope.headings=[];

    $scope.uploadMessage="";
    
    $scope.transactionGrid = {
        data: 'validTransactions',
        plugins: [new ngGridFlexibleHeightPlugin()],
        columnDefs:$scope.transactionHeadings
    }
    
    //Add a watcher to the data, we will be continually re-evaluating if the user's transactions meet the basic validation rules
    //1. Match the correct number of fields
    //2. Date field is valid
    //3. Monetary anounts are valid
    $scope.$watch('transactionText', function() { 
    	if($scope.transactionText==undefined || $scope.transactionText=="") {
    		return;
    	}
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
    
    //Upload the valid transactions
    $scope.upload = function() {
        $scope.invalidTransactions=[];
        $scope.validTransactions=[];
        $scope.transactionsWithUnknownCategory=[];
        $scope.transactionService.upload($scope.selectedAccount,$scope.validTransactions);
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
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Currently signed in user
	$scope.user=configuration.user;
	
	$scope.transactionService=transactionService;
	$scope.accountService=accountService;
    
	$scope.rawPaste = '';
    $scope.parsedPaste = [];
    $scope.json="some Json";
    $scope.rawTransactions="";
    $scope.colDefs=[];
    $scope.selectedTransactions=[];
    $scope.userAccounts=[];
    $scope.viewTrans=false;
    $scope.viewTransactionUpload=false;
    $scope.selectedAccount={};
    
    // Grid ///////////////////////////////////////////////////////////////////
    $scope.gridOptions = {
    	    data: 'selectedTransactions',
    	    columnDefs: 'colDefs',
    	    enableColumnResize: false,
    	    showGroupPanel: true,
    	    plugins: [new ngGridFlexibleHeightPlugin()]	
   };
   
    //Add a watcher to the data, each time it changes we recreate the colsDef
    $scope.$watch('selectedTransactions', function() { 
    	$scope.updateSelectedTransactions(); 
    });
   
    
    $scope.updateSelectedTransactions = function() {
    	$scope.colDefs = [];
    	if($scope.selectedTransactions.length > 0) {
	    	 angular.forEach(Object.keys($scope.selectedTransactions[0]), function(key){
	    	    $scope.colDefs.push({ field: key });
	    	  });
	      }
    }
    
    ///////////////////////////////////////////////////////////////////
    
/*    //This 
    $scope.viewTransactions = function() {
    	$scope.viewTrans=true;
    	$scope.viewTransactionUpload=false;
    	console.log("Loading Transactions for Account "+JSON.stringify($scope.selectedAccount.name));
    	transactionPromise = $scope.transactionService.getTransactions($scope.selectedAccount)
    						 	.then(
									function(transactions) {
										console.log("Retrived " + transactions.length + " transactions for "+ accountName);
										$scope.selectedTransactions=transactions;
									},
									function(httpError) {
										console.log("Error Retrieving transactions for selectedTransactions");
								});
    }
*/    
    //This 
    $scope.viewTransUploadArea = function() {
    	$scope.viewTrans=false;
    	$scope.viewTransactionUpload=true;
    	transactions = $scope.transactionService.getTransactions($scope.selectedAccount.id);
    	$scope.selectedTransactions=transactions;
    	//$scope.updateSelectedTransactions();
    }
    
    $scope.uploadTransactions = function() {
    	console.log("Uploading transactions.....");
    	isValid = validateUploadTrasactions($scope.rawTransactions);
    	if(isValid) {
    		console.log("Passed Validation")
    	}
    	else {
    		console.log("Failed Validation");
    	}

    	
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
    
    $scope.clear = function() {
    	$scope.selectedTransactions=[];
        $scope.rawPaste = '';
        $scope.parsedPaste = [];
        $scope.rawTransactions="";
    }
        
    $scope.uploadBoxEmpty = function() {
    	textAreaContent = $scope.rawTransactions;
    	return (textAreaContent == "");
    }
    
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
                	 console.log("Detected a row with incorrect number of columns ["+cols+"]");
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