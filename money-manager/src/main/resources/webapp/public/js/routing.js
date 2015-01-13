//Loads the module...
var moneyManagerApp = angular.module('money-manager');

//Define our routes
moneyManagerApp.config(['$routeProvider',
    function($routeProvider) {
	    $routeProvider.
	      when('/transactions', {
	        templateUrl: 'partials/transactions.html',
	        controller: 'transactionController'
	      }).
	      when('/accounts', {
	    	  templateUrl: 'partials/accounts.html',
		      controller: 'accountController'
	      }).
	      when('/', {
	    	  templateUrl: 'partials/home.html',
	      });
	      

}]);