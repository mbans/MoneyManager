var module = angular.module('money-manager');

//Add the 'PasteController' as a 
module.controller('ruleController',  function ($scope, $http, transactionService, ruleService) {
	
	//Currently signed in user
	$scope.user=configuration.user;
	$scope.userRules=[];
	$scope.ruleService=ruleService; 
	
	//Add an empty rule 
	$scope.addNewRuleEntry= function() {
    	var newRule={};
    	newRule.owner=$scope.user;
    	$scope.userRules.push(newRule)
    }
    
    //Removes rule from view
    $scope.deleteRule = function(indexToRemoveItem) {
    	console.log("Deleting rule " + $scope.userRules[indexToRemoveItem].name);
    	$scope.userRules.splice(indexToRemoveItem,1);
    }
    
    //Save rules 
    $scope.saveRules = function() {
    	var request={"user":$scope.user, "rules":$scope.userRules};
    	
		return $http({
			   url: configuration.baseUrl+"/rules/", 
			   method:"POST",
			   data: JSON.stringify(request),
			   headers: {"Content-Type":"application/json"}
			  })
		.success(function() {
	    	bootbox.alert("Your rules have been successfully saved");
	    	$scope.getUserRules();
		}).
		error(function(message) {
			bootbox.alert("Boo! Recieved an error saving your rules");
		});
    }
    
    //Retrieve rules for user
    $scope.getUserRules = function() {
		return $http({url: configuration.baseUrl+"/rules/"+$scope.user, method:"GET"})
		.success(function(rules) {
			console.log("Retrieved "+rules.length+ " for "+$scope.user);
			$scope.userRules=rules;
		}).
		error(function(error) {
			bootbox.alert("Ah oh! There seems an issue retrieving your rules, close browser and try again.");
		});
    }
    
	$scope.getUserRules();	
});