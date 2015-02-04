var module = angular.module('money-manager');

//Add the 'PasteController' as a 
module.controller('accountController',  function ($scope, accountService, transactionService) {
	
	//Currently signed in user
	$scope.user=configuration.user;
	
	$scope.accountIsLoaded=false;
	$scope.newAccountChosen=false;
	
	$scope.userAccounts={};
	$scope.accountService=accountService;
	$scope.transactionServie=transactionService;
	$scope.account={};

	$scope.account.transactionHeadingOrdering=[];
	
	//Retrieves user accounts for the current user
    $scope.getUserAccounts = function() {
    	console.log("In cotroller.getUserAccounts()");
		var userAccountsPromise=$scope.accountService.getUserAccounts($scope.user);
		userAccountsPromise.then(
			function(accounts) {$scope.userAccounts=accounts;},
			function(errorMessage) {console.log("Error retrieving Accounts for "+$scope.user + " error="+errorMessage);}
		);
    }
    
	
    //Add a watcher to the 'sampleTransaction' and use it to populate the sampleTransactionEntries.
    //These are used to create the drop downs that user maps entries to.
    $scope.$watch('account.sampleTransaction', function() { 
    	if($scope.account.sampleTransaction != undefined) {
    		$scope.sampleTransactionEntries=$scope.account.sampleTransaction.split("\t");
    	}
    });
    
    //Check the given fieldName is populated
    $scope.checkFieldIsPopulated = function(fieldName, transactionHeadingOrdering) {
    	for(var i=0; i<transactionHeadingOrdering.length; i++) {
    		transactionHeadingOrdering[i]=transactionHeadingOrdering[i].trim();
    		value=transactionHeadingOrdering[i];
    		if(value.indexOf(fieldName) != -1) {
    			return true;
    		}
    	}
    	return false
    }
    
    $scope.getSmallVersion = function(fieldName) {
    	if(fieldName.length > 15) {
    		return fieldName.substring(0,14)+"...";
    	}
    	return fieldName;
    }
    

    $scope.cancelAddOrUpdate = function() {
    	$scope.newAccountChosen=false;
    	$scope.accountIsLoaded=false;
    }
    
    //Load the selected account
    $scope.loadAccount = function(accountToLoad) {
    	$scope.account=accountToLoad;
    	$scope.accountIsLoaded=true;
    }
    
    //Initialise when user choses to create new account
    $scope.chosenToAddAccount = function() {
    	$scope.accountIsLoaded=false;
    	$scope.newAccountChosen=true;
    	$scope.account={};
    	$scope.account.accountOwner=$scope.user;
    	$scope.account.transactionHeadingOrdering=[];
        $scope.sampleTransactionEntries=[];
    }

    //Delete Account
    $scope.deleteAccount = function(account) {
    		console.log("Deleteing account "+ account.name);
    		$scope.accountService.deleteAccount(account, $scope.getUserAccounts);
    }
    
    //Add or Update the account
    $scope.addOrUpdateAccount = function() {
    	//Always set the delimiter - need to have user set this eventually
    	$scope.account.delimiter="Tab";
    	
		//Update The Account
		for(var i=0; i<$scope.account.transactionHeadingOrdering.length; i++) {
			$scope.account.transactionHeadingOrdering[i] = $scope.account.transactionHeadingOrdering[i].trim();
		}
		
		//We are updating
    	if($scope.accountIsLoaded) {
    		var promise=$scope.accountService.updateAccount($scope.account);
    		promise.then(
    			function() {
    				bootbox.alert("Woo hoo! Successfully updated account "+$scope.account.name + ".");
    				$scope.accountIsLoaded=false;
    				$scope.getUserAccounts();
    			},
    			function(errorMessage) {
    				bootbox.alert("Bummer! There was an error updating account "+ $scope.account.name + ". Error Message ("+JSON.stringify(errorMessage)+")");
    			}
    		);
    	}
    	else {
    		//Save the new account
    		var addAccountPromise=$scope.accountService.saveAccount($scope.account);
    		addAccountPromise.then(
    			function() {
    				bootbox.alert("Woo hoo! Successfully added account "+$scope.account.name + ".");
    				$scope.getUserAccounts(); //Reload users account to reflect new one being added
    				$scope.newAccountChosen=false;
    			},
    			function(errorMessage) {    				
    				bootbox.alert("Bummer! An error encountered adding "+$scope.account.name + ". Error Message ("+JSON.stringify(errorMessage)+")");
    			}
    		);
    	}
	}
    
    //Returns an array of Field Heading names that we have not assigned against the sample transaction yet.
    //This is used by UI to inform user prior to saving the transaction
    $scope.getUnpopulatedMandatoryFields = function() {
    	unpopulatedMandatoryFields=[];
    	for(var i=0; i<$scope.transactionFieldHeadings.length; i++) {
    		fieldName=$scope.transactionFieldHeadings[i].name;
    		var isMandatory= ($scope.transactionFieldHeadings[i].mandatory == "true");
    		if(isMandatory) {
    			//Check the transactionHeadingOrdering to see we have an entry for this field
    			fieldIsPopulated=$scope.checkFieldIsPopulated(fieldName, $scope.account.transactionHeadingOrdering);
    			if(!fieldIsPopulated) {
    				unpopulatedMandatoryFields[unpopulatedMandatoryFields.length]=fieldName;
    				console.log("Detected "+fieldName+" is not populated.");
    			}	
    		}
    	}
    	return unpopulatedMandatoryFields;
    }
    
	//Constant
					$scope.transactionFieldHeadings = transactionService.transactionFieldHeadings;
	
	//Get the user accounts on start-up
	$scope.getUserAccounts();
});