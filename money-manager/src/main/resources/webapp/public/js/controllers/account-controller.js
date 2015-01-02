var module = angular.module('money-manager');

//Add the 'PasteController' as a 
module.controller('accountController',  function ($scope, accountService, transactionService) {
    
	$scope.accountService=accountService;
	$scope.transactionServie=transactionService;
	$scope.addAccount=false;
	$scope.account={};
	$scope.account.transactionHeadingOrdering=[];
	$scope.sampleTransaction="";

	//This is a boolean to indicate that the user has assigned all manadatory fields
	$scope.allMandatoryFieldsAssigned=false;
	
	//Constant
	$scope.transactionFieldHeadings=transactionService.transactionFieldHeadings;
	
    //Add a watcher to the 'sampleTransaction' and populate the drop downs with it
    $scope.$watch('sampleTransaction', function() { 
    	$scope.sampleTransactionEntries=$scope.sampleTransaction.split("\t");
    	$scope.account.sampleTransaction=$scope.sampleTransaction;
    });
    
    $scope.getMandatoryFields = function() {
    	for(var i=0; i<$scope.transactionFieldHeadings.length; i++) {
    		transactionName=$scope.transactionFieldHeadings[i].name;
    		var isMandatory= ($scope.transactionFieldHeadings[i].mandatory == "true");
    		if(isMandatory) {
    			console.log(transactionName + "is mandatory");
    		}
    	}
    }
    
    
    $scope.ping = function() {
    	return $scope.accountService.ping();
    }
	
    $scope.numberOfColsInSampleTransacion = function() {
    	return 5;
    }
    
    $scope.submitForm = function(isValid) {
    	$scope.getMandatoryFields();
    	alert("Account = " + JSON.stringify($scope.account));
//    	transactioHeadings=$scope.account.transactionHeadings;
//    	transactioHeadingKeys=Object.keys(transactioHeadings);
//    		
//    	
//    	
//    	
//    	for(var i=0; i<transactioHeadingKeys.length; i++) {
//    		headingName=transactioHeadingKeys[i];
//    		headingValue=transactioHeadings[headingName];
//    		
//    		//Now look up the sampleTransaction to see what column that this heading corrensponds to
//    		for(var j=0; j<$scope.sampleTransactionEntries.length;j++) {
//    			sampleTransactionValue=$scope.sampleTransactionEntries[j];
//    			if(headingValue==sampleTransactionValue) {
//    				$scope.account.transactionHeadingIndexes[headingName]=j;
//    				break;
//    			}
//    		}
//    	}
//    	console.log("Transaction Heading Indexes="+JSON.stringify($scope.account.transactionHeadingIndexes));
    	
    }
    
    
    $scope.getSmallVersion = function(fieldName) {
    	if(fieldName.length > 20) {
    		return fieldName.substring(0,19)+"...";
    	}
    	return fieldName;
    }
    
    console.log("Inside the account controller = "+$scope.accountDetails);
});