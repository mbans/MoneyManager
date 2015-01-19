var app = angular.module('money-manager');

app.service('accountService', function($http,$q) {
	
	this.savedAccountSuccess=undefined;
	
	this.saveAccount = function(accountToSave) {
		return $http({
				   url: configuration.baseUrl+"/account/", 
				   method:"POST",
				   data: JSON.stringify(accountToSave),
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
	
	this.deleteAccount = function(account, accountRefresh) {
		return $http({
			   url: configuration.baseUrl+"/account/", 
			   method:"DELETE",
			   data: JSON.stringify(account),
			   headers: {"Content-Type":"application/json"}
			  })
		.success(function() {
			bootbox.alert("See ya Account! Successfully deleted "+account.name);
			accountRefresh();
		}).
		error(function(message) {
			bootbox.alert("Boo! Recieved an error when deleting account "+account.name+".");
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