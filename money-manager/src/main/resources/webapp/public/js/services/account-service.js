var app = angular.module('money-manager');

app.service('accountService', function($http,$q) {
	
	this.savedAccountSuccess=undefined;
	
	this.ping = function() {
		$http.get(configuration.baseUrl+"/account/ping")
		.success(function(resp) {
			console.log("Response from 'ping' = "+resp);
			return resp
		});
	}
	
	this.saveAccount = function(accountToSave) {
		$http({
			   url: configuration.baseUrl+"/account/", 
			   method:"POST",
			   data: JSON.stringify(accountToSave),
			   headers: {"Content-Type":"application/json"}
			  }).
			success(function(message) {
//				_this.savedAccountSuccess=true;
				alert("Saved successfully");
			}).
			error(function(message) {
//				_this.savedAccountSuccess=false;
				alert("Saved failed "+message);
			});
		}

	this.getUserAccounts = function(userName) {
		$http.get(configuration.baseUrl+"/account/"+userName)
		.success(function(accounts) {
//			_this.errorMessage=undefined
			console.log("Retrived " + accounts.length + " accounts for "+ userName);
			return accounts;
		}).
		error(function(message) {
			console.log("Error retrieving accounts for user")
		});
	}
	
});