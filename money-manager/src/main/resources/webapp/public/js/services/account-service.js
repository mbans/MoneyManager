var app = angular.module('money-manager');

app.service('accountService', function($http,$q) {
	
	this.savedAccountSuccess=undefined;
	
	this.saveAccount = function(accountToSave) {
	
		return $http({
				   url: configuration.baseUrl+"/account/", 
				   method:"POST",
				   data: JSON.stringify(accountToSave),
				   headers: {"Content-Type":"application/json"}
				  }).
			success(function(message) {
				alert("Saved successfully");
			}).
			error(function(message) {
				alert("Saved failed "+message);
			});
		}
	
	/**
	 * Retrieves all for the given user
	 */
	this.getUserAccounts = function(userName) {
		//Return the promise
		console.log("Retrieving accounts for "+userName);
		return $http.get(configuration.baseUrl+"/account/"+userName)
		.then(
			function(response) {
				var accounts=response.data;
				console.log("Retrived " + accounts.length + " accounts for "+ userName);
				return accounts;
			},
			function(httpError) {
				console.log("Error Retrieving accounts...");
			});
	}

});