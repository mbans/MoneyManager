var app = angular.module('money-manager');

app.service('accountService', function($http) {
	
	this.ping = function() {
		$http.get("http://localhost:4567/account/ping")
		.success(function(resp) {
			console.log("Response from 'ping' = "+resp);
			return resp
		});
	}
	

	
});