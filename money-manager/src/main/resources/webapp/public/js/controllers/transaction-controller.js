//Loads the money-manager modue
var module = angular.module('money-manager');


//Add the 'PasteController' as a 
module.controller('transactionController',  function ($scope, transactionService) {
    $scope.rawPaste = '';
    $scope.parsedPaste = [];
    $scope.json="some Json";
    $scope.rawTransactions="";
    $scope.colDefs=[];
    $scope.selectedTransactions=[];
    $scope.transactionService=transactionService;
    $scope.accountDetails=[];
    $scope.viewTrans=false;
    $scope.viewTransactionUpload=false;
    
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
    
    //This 
    $scope.viewTransactions = function() {
    	$scope.viewTrans=true;
    	$scope.viewTransactionUpload=false;
    	console.log("Loading Transactions for selectedAccount "+JSON.stringify($scope.selectedAccount.details));
    	transactions = $scope.transactionService.getTransactions($scope.selectedAccount.details.id);
    	$scope.selectedTransactions=transactions;
    }
    
    //This 
    $scope.viewTransUploadArea = function() {
    	$scope.viewTrans=false;
    	$scope.viewTransactionUpload=true;
    	transactions = $scope.transactionService.getTransactions($scope.selectedAccount.details.id);
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
    
    //TODO takes a user id 
    $scope.getAccountDetailsForDropdown = function() {
    	$scope.accountDetails=$scope.transactionService.getAccountsForUser("");
    	console.log("Retrieved accounts....");
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
    $scope.getAccountDetailsForDropdown();
    
    //By default we select the first account for the user
    $scope.selectedAccount=$scope.transactionService.getAccountsForUser("")[0];
    
    console.log("AccountDetails = "+$scope.accountDetails);
});