var module = angular.module('money-manager');

//Add the 'PasteController' as a 
module.controller('accountController',  function ($scope, accountService, transactionService) {
	$scope.user="martin"
	$scope.userAccounts=[];
	
	$scope.accountService=accountService;
	$scope.transactionServie=transactionService;
	$scope.account={};
	$scope.account.transactionHeadingOrdering=[];
	
	$scope.account.accountOwner="martin";
	$scope.account.bank="RBS";
	$scope.account.name="Martin-RBS";
	$scope.account.accountNum=12345678;
	$scope.account.sortCode=111111;
	$scope.sampleTransaction="22-Dec-2012\tPOS\tTedBaker\t-\t12.00\t1212";

	
	//Retrieves user accounts for the current user
    $scope.getUserAccounts = function() {
		$scope.userAccounts=$scope.accountService.getUserAccounts($scope.user);
//		console.log($scope.userAccounts.length+ " user accounts retrieved for "+$scope.user);
    }
	
    //Add a watcher to the 'sampleTransaction' and use it to populate the sampleTransactionEntries.
    //These are used to create the drop downs that user maps entries to.
    $scope.$watch('sampleTransaction', function() { 
    	$scope.sampleTransactionEntries=$scope.sampleTransaction.split("\t");
    	$scope.account.sampleTransaction=$scope.sampleTransaction;
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

    //Submits the Account form to either update or save the Account information
    $scope.submitForm = function(isValid) {
    	fields=$scope.getUnpopulatedMandatoryFields();
    	if(fields.length>0) {
	    	bootbox.alert("Ensure the following fields are all populated: "+fields.toString());
    	}
    	else {
    		$scope.accountService.saveAccount($scope.account);
    	}
    }

    
    $scope.getSmallVersion = function(fieldName) {
    	if(fieldName.length > 20) {
    		return fieldName.substring(0,19)+"...";
    	}
    	return fieldName;
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
	$scope.transactionFieldHeadings=transactionService.transactionFieldHeadings;
	
	//Get the user accounts on start-up
	$scope.getUserAccounts();

});